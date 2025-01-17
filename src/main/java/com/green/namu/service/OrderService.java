package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Menu;
import com.green.namu.domain.Order;
import com.green.namu.domain.Store;
import com.green.namu.domain.User;
import com.green.namu.domain.status.OrderStatus;
import com.green.namu.dto.OrderRequest;
import com.green.namu.dto.OrderResponse;
import com.green.namu.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    // 장바구니 화면에서 주문 요청 처리
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {

        // 1. 요청 데이터 검증 (INVALID_REQUEST_DATA)
        if (request.getStoreId() == null || request.getMenus() == null || request.getMenus().isEmpty() ||
                request.getPaymentMethod() == null || request.getTotalPrice() < 0) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST_DATA);
        }

        // 2. 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));

        // 3. 가게 검증
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_STORE_ID));

        // 4. 결제 수단 검증
        List<String> validPaymentMethods = List.of("현장결제"); // TODO: 결제 기능 추가 되면, 리스트 값 추가 -> List.of("현장결제", "카드결제", "계좌이체");
        if (!validPaymentMethods.contains(request.getPaymentMethod())) {
            throw new BaseException(BaseResponseStatus.INVALID_PAYMENT_METHOD);
        }

        // 5. 가게 메뉴 검증
        for (OrderRequest.OrderMenuRequest menuRequest : request.getMenus()) {
            Menu menu = menuRepository.findById(menuRequest.getMenuId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MENU_ID));

            if (!menu.getStore().getStoreId().equals(store.getStoreId())) {
                throw new BaseException(BaseResponseStatus.MENU_NOT_IN_STORE); // 새로운 에러 상태 추가 필요
            }
        }

        // 6. 장바구니 검증 및 메뉴 데이터 검증, 총 할인 금액 계산, 주문한 세트 이름 저장
        int totalDiscount = 0;
        StringBuilder setNameBuilder = new StringBuilder();
        for (OrderRequest.OrderMenuRequest menuRequest : request.getMenus()) {
            if (menuRequest.getCartQuantity() == 0) {
                throw new BaseException(BaseResponseStatus.CART_EMPTY);
            }

            Menu menu = menuRepository.findById(menuRequest.getMenuId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MENU_ID));

            totalDiscount += (menu.getMenuPrice() - menu.getMenuDiscountPrice() * menuRequest.getCartQuantity());

            if (!setNameBuilder.isEmpty()) {
                setNameBuilder.append(", ");
            }
            setNameBuilder.append(menu.getSetName());
        }

        // 7. User의 total_discount 값 업데이트
        user.setTotalDiscount(user.getTotalDiscount() + totalDiscount);

        // 8. 주문 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .paymentMethod(request.getPaymentMethod())
                .totalPrice(request.getTotalPrice())
                .status(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);

        // 9. Store의 order_count 값 증가
        store.setOrderCount(store.getOrderCount() + 1);

        // 10. 장바구니 초기화 (status를 INACTIVE로 변경)
        cartRepository.updateStatusByUserId(userId); // TODO : 이 과정이 꼭 필요한 건가?

        // 11. 응답 생성
        return new OrderResponse(
                order.getOrderId(),
                order.getStatus().toString(),
                setNameBuilder.toString(),
                order.getTotalPrice(),
                store.getStoreName(),
                store.getStoreAddress(),
                store.getStorePhone(),
                store.getPickupTime()
        );

    }
}

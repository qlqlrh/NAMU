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
    // TODO : INVALID_REQUEST_DATA, CART_EMPTY 미구현, AUTHENTICATION_FAILED, FORBIDDEN_ACCESS_ORDER는 토큰 때문에 확인 불가 상태
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        // 1. 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));

        // 2. 가게 검증
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_STORE_ID));

        // 3. 결제 수단 검증
        List<String> validPaymentMethods = List.of("현장결제"); // TODO: 결제 기능 추가 되면, 리스트 값 추가 -> List.of("현장결제", "카드결제", "계좌이체");
        if (!validPaymentMethods.contains(request.getPaymentMethod())) {
            throw new BaseException(BaseResponseStatus.INVALID_PAYMENT_METHOD);
        }

        // 4. 총 할인 금액 계산 및 주문한 세트 이름 저장
        int totalDiscount = 0;
        StringBuilder setNameBuilder = new StringBuilder();
        for (OrderRequest.OrderMenuRequest menuRequest : request.getMenus()) {
            Menu menu = menuRepository.findById(menuRequest.getMenuId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MENU_ID));

            totalDiscount += (menu.getMenuPrice() - menu.getMenuDiscountPrice() * menuRequest.getCartQuantity());

            if (!setNameBuilder.isEmpty()) {
                setNameBuilder.append(", ");
            }
            setNameBuilder.append(menu.getSetName());
        }

        // 5. User의 total_discount 값 업데이트
        user.setTotalDiscount(user.getTotalDiscount() + totalDiscount);

        // 6. 주문 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .paymentMethod(request.getPaymentMethod())
                .totalPrice(request.getTotalPrice())
                .status(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);

        // 7. Store의 order_count 값 증가
        store.setOrderCount(store.getOrderCount() + 1);

        // 8. 장바구니 초기화 (status를 INACTIVE로 변경)
        cartRepository.updateStatusByUserId(userId); // TODO : 이 과정이 꼭 필요한 건가?

        // 9. 응답 생성
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

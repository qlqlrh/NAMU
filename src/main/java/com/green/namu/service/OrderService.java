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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    // 장바구니 화면에서 주문 요청 처리
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        // 1. 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));

        // 2. 가게 검증
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_STORE_ID));

        // 3. 총 할인 금액 계산 및 주문한 세트 이름 저장
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

        // 4. User의 total_discount 값 업데이트
        user.setTotalDiscount(user.getTotalDiscount() + totalDiscount);

        // 5. 주문 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .paymentMethod(request.getPaymentMethod())
                .totalPrice(request.getTotalPrice())
                .status(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);

        // 6. Store의 order_count 값 증가
        store.setOrderCount(store.getOrderCount() + 1);

        // 7. 장바구니 초기화 (status를 INACTIVE로 변경)
        cartRepository.updateStatusByUserId(userId); // TODO : 이 과정이 꼭 필요한 건가?

        // 8. 응답 생성
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

package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.OauthMember;
import com.green.namu.domain.Order;
import com.green.namu.domain.Store;
import com.green.namu.domain.User;
import com.green.namu.dto.LoginDto;
import com.green.namu.dto.MyPageRes;
import com.green.namu.dto.RegisterDto;
import com.green.namu.repository.OrderRepository;
import com.green.namu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public User saveUserFromOauth(OauthMember oauthMember) {
        // OauthMember를 기반으로 User 엔티티 생성
        User user = User.fromOauthMember(oauthMember);

        // users 테이블에 저장
        return userRepository.save(user);
    }

    @Transactional
    public User findOrCreateUserFromOauth(OauthMember oauthMember) {
        // OauthMember를 기반으로 User가 이미 존재하는지 확인
        return userRepository.findByEmail(oauthMember.getEmail()) // 이메일을 기준으로 조회
                .orElseGet(() -> saveUserFromOauth(oauthMember)); // 없으면 새로 저장
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        // Refresh Token 업데이트
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setRefreshToken(refreshToken); // User 엔티티에 setter 추가 필요
        userRepository.save(user);
    }

    public MyPageRes getMyPage(Long userId) {
        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));

        // 최근 주문 정보 조회
        Order lastOrder = orderRepository.findFirstByUserOrderByOrderTimeDesc(user);

        // 주문 여부 및 가게 사진 URL과 픽업 시간 처리
        boolean isOrder = (lastOrder != null);
        String storePictureUrl = null;
        String pickupTime = null;

        if (isOrder) {
            Store store = lastOrder.getStore();
            // storePictureUrls가 null이 아니고, 비어있지 않을 경우 첫 번째 URL 가져오기
            if (store.getStorePictureUrls() != null && !store.getStorePictureUrls().isEmpty()) {
                storePictureUrl = store.getStorePictureUrls().get(0);
            }
            pickupTime = store.getPickupTime();
        }

        // 응답 생성
        return new MyPageRes(
                user.getProfileUrl(),
                user.getUserName(),
                user.getTotalDiscount(),
                isOrder,
                storePictureUrl,
                pickupTime
        );
    }

    public User register(RegisterDto registerDto) {
        User user = User.createUser(registerDto.getUserName(), registerDto.getPassword(), registerDto.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findUser(int id) {
        return userRepository.findById((long) id).orElseThrow(()-> {
            return new IllegalArgumentException("User ID를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return user;
    }
}
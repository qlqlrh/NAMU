package com.green.namu.service;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.User;
import com.green.namu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
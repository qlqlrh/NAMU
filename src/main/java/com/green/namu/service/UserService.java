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
}
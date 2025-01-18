package com.green.namu.service;

import com.green.namu.domain.OauthMember;
import com.green.namu.domain.User;
import com.green.namu.dto.LoginDto;
import com.green.namu.dto.RegisterDto;
import com.green.namu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUserFromOauth(OauthMember oauthMember) {
        // OauthMember를 기반으로 User 엔티티 생성
        User user = User.fromOauthMember(oauthMember);

        // users 테이블에 저장
        return userRepository.save(user);
    }

    @Transactional
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
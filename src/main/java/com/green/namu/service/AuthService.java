package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.User;
import com.green.namu.domain.status.Status;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.repository.UserRepository;
import com.green.namu.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public PostLoginRes generateToken(Long userId) {
        // 1. 사용자(userId) 검증

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID));

        if (!user.getUserStatus().equals(Status.ACTIVE)) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_STATUS);
        }

        // 2. Access Token 및 Refresh Token 생성
        String accessToken = jwtService.createAccessToken(user.getUserId());
        String refreshToken = jwtService.createRefreshToken(user.getUserId());

        user.setRefreshToken(refreshToken); // Refresh Token 저장
        userRepository.save(user); // 데이터베이스에 저장

        // 3. 응답 반환
        return new PostLoginRes(accessToken, refreshToken);
    }
}

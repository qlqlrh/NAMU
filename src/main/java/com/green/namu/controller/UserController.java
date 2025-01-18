package com.green.namu.controller;

import com.green.namu.common.response.Response;
import com.green.namu.domain.User;
import com.green.namu.dto.LoginDto;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.dto.RegisterDto;
import com.green.namu.service.UserService;
import com.green.namu.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "전체 회원 보기", description = "전체 회원을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members")
    public Response<?> findAll() {
        return new Response<>("true", "조회 성공", userService.findAll());
    }

    @Operation(summary = "유저 찾기", description = "개별 유저 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{id}")
    public Response<?> findUser(@PathVariable("id") Integer id) {
        return new Response<>("true", "조회 성공", userService.findUser(id));
    }

    @Operation(summary = "회원가입", description = "회원가입 진행")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response<?> register(@RequestBody RegisterDto registerDto) {
        return new Response<>("true", "User created successfully", userService.register(registerDto));
    }

    @Operation(summary = "로그인", description = "로그인 처리")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public Response<?> login(@RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        String accessToken = jwtService.createAccessToken(user.getUserId());
        String refreshToken = jwtService.createRefreshToken(user.getUserId());
        return new Response<>("true", "로그인 성공", new PostLoginRes(accessToken, refreshToken));
    }

    @Operation(summary = "로그아웃", description = "로그아웃 처리")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public Response<?> logout(HttpServletRequest request) {
        String token = jwtService.getJwt();
        jwtService.invalidateToken(token); // 토큰 블랙리스트 처리
        return new Response<>("true", "로그아웃 성공", null);
    }
}

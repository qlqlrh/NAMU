package com.green.namu.controller;

<<<<<<< HEAD
import com.green.namu.common.exceptions.BaseException;
=======
>>>>>>> origin/master
import com.green.namu.common.response.BaseResponse;
import com.green.namu.domain.OauthServerType;
import com.green.namu.dto.PostLoginRes;
import com.green.namu.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import static org.hibernate.query.sqm.tree.SqmNode.log;

=======
>>>>>>> origin/master
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows // checked exception을 숨기고, 자동으로 예외 처리를 해 줌
    @GetMapping("/{oauthServerType}")
    public ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable(name = "oauthServerType") OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);

        // 사용자가 브라우저 -> OAuth 서버로 리다이렉트 되어 인증 진행하도록 함
        response.sendRedirect(redirectUrl); // 여기서 발생 가능한 IOException 예외를 @SneakyThrows가 자동으로 처리
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    public BaseResponse<PostLoginRes> login(
            @PathVariable(name = "oauthServerType") OauthServerType oauthServerType,
            @RequestParam(name = "code") String code
    ) {
<<<<<<< HEAD
        try {
            PostLoginRes loginResponse = oauthService.login(oauthServerType, code);
            return new BaseResponse<>(loginResponse);
        } catch (BaseException e) {
            log.error("소셜 로그인 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
=======
        PostLoginRes loginResponse = oauthService.login(oauthServerType, code);
        return new BaseResponse<>(loginResponse);
>>>>>>> origin/master
    }
}

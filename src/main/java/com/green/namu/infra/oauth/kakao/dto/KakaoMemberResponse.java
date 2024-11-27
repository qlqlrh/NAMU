package com.green.namu.infra.oauth.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.green.namu.domain.OauthId;
import com.green.namu.domain.OauthMember;
import java.time.LocalDateTime;

import static com.green.namu.domain.OauthServerType.KAKAO;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberResponse(
        /*
            AccessToken을 통해 받아온 사용자 정보(응답) 매핑 클래스
            참고 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
         */
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    // 받아온 JSON 정보를 토대로 OauthMember 생성
    /*
        {
          "id": 123456789,
          "kakao_account": {
            "profile": {
              "nickname": "John Doe",
              "thumbnail_image_url": "http://example.com/image.jpg"
            }
          }
        }
     */
    public OauthMember toDomain() {
        return OauthMember.builder()
                .oauthId(new OauthId(String.valueOf(id), KAKAO))
                .nickname(kakaoAccount.profile.nickname)
                .profileImageUrl(kakaoAccount.profile.profileImageUrl)
                .build();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl, // 프로필 미리보기 이미지 URL
            String profileImageUrl, // 프로필 사진 URL
            boolean isDefaultImage // 프로필 사진 URL이 기본 프로필 사진 URL인지 여부
    ) {
    }
}

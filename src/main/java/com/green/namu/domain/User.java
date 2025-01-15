package com.green.namu.domain;

import com.green.namu.common.entity.BaseEntity;
import com.green.namu.domain.status.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "password") // 소셜 로그인 때문에 nullable
    private String password;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "profile_url", nullable = false)
    private String profileUrl;

    @Column(name = "user_phone", length = 13)  // 소셜 로그인 때문에 nullable
    private String userPhone;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_type", nullable = false)
    private JoinType joinType;

    @Column(name = "grade", length = 100) // TODO: 필요한가?
    private String grade;

    @Column(name = "eco_points", nullable = false)
    private Integer ecoPoints = 0;

    @Column(name = "total_discount", nullable = false)
    private Integer totalDiscount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // MERCHANT or CUSTOMER

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private Status userStatus; // ACTIVE or INACTIVE

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    // 추가 생성자 (OauthMember 기반 User 생성)
    public static User fromOauthMember(OauthMember oauthMember) {
        return User.builder()
                .userName(oauthMember.getNickname())
                .profileUrl(oauthMember.getProfileImageUrl())
                .email(oauthMember.getEmail()) // 이메일은 OauthMember로부터 받아오거나 따로 처리
                .role(Role.CUSTOMER) // 기본 역할 설정
                .userStatus(Status.ACTIVE)
                .ecoPoints(0)
                .totalDiscount(0)
                .joinType(JoinType.valueOf("KAKAO"))
                .build();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public void addCart(Cart cart) {
        this.carts.add(cart);
        cart.setUser(this);
    }

    public void setTotalDiscount(Integer totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
    public enum JoinType {
        INAPP, // 자체 이메일 가입
        KAKAO  // 카카오 가입
    }

    public enum Role {
        MERCHANT, // 가게 사장
        CUSTOMER  // 소비자
    }
}

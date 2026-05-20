package org.sopt.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")  // "user"는 SQL 예약어라 테이블명을 변경해요
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(unique = true)
    private Long kakaoId;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    public static User createGeneralUser(String nickname, String email, String password) {
        User user = new User();
        user.nickname = nickname;
        user.email = email;
        user.password = password;
        user.loginType = LoginType.GENERAL;
        return user;
    }

    public static User createKakaoUser(String nickname, Long kakaoId, String email) {
        User user = new User();
        user.nickname = nickname;
        user.kakaoId = kakaoId;
        user.email = email;
        user.loginType = LoginType.KAKAO;
        return user;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
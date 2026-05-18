package org.sopt.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")  // "user"는 SQL 예약어라 테이블명을 변경해요
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String password;

    private String email;

    protected User() {}

    public User(String nickname, String password,String email) {
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.request.SignUpRequest;
import org.sopt.auth.exception.code.AuthErrorCode;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.global.api.exception.BaseException;
import org.sopt.auth.jwt.JwtService;
import org.sopt.user.dto.UserResponse;
import org.sopt.user.entity.LoginType;
import org.sopt.user.entity.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository memberRepository;
    private final JwtService jwtService;
    private final KakaoService kakaoService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final BlacklistService blacklistService;

    public UserResponse loginWithCredentials(String email, String password) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse signUp(SignUpRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new BaseException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.createGeneralUser(
                request.nickname(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        memberRepository.save(user);


        return generateAndSaveTokens(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BaseException(AuthErrorCode.SIGNUP_REQUIRED));

        // 카카오 유저가 일반 로그인 시도하면 막기
        if (user.getLoginType() == LoginType.KAKAO) {
            throw new BaseException(AuthErrorCode.KAKAO_USER_CANNOT_LOGIN);
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BaseException(AuthErrorCode.INVALID_PASSWORD);
        }

        return generateAndSaveTokens(user);
    }

    @Transactional
    public TokenResponse kakaoLogin(String code) {
        // 1. 인가코드로 카카오 액세스토큰 받기
        String kakaoAccessToken = kakaoService.getAccessToken(code);

        // 2. 카카오 액세스토큰으로 유저 정보 가져오기
        KakaoService.KakaoUserInfo kakaoUserInfo = kakaoService.getUserInfo(kakaoAccessToken);

        // 3. 유저 조회 or 신규 생성
        User user = memberRepository.findByKakaoId(kakaoUserInfo.kakaoId())
                .orElseGet(() -> memberRepository.save(
                        User.createKakaoUser(
                                kakaoUserInfo.nickname(),
                                kakaoUserInfo.kakaoId(),
                                kakaoUserInfo.email()
                        )
                ));

        user.updateNickname(kakaoUserInfo.nickname());

        // 이메일이 새로 들어왔으면 업데이트
        if (kakaoUserInfo.email() != null && user.getEmail() == null) {
            user.updateEmail(kakaoUserInfo.email());
        }


        return generateAndSaveTokens(user);
    }

    public TokenResponse reissue(String refreshToken) {
        if (refreshToken == null) {
            throw new BaseException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long memberId = jwtService.verifyAndGetMemberId(refreshToken);
        User user = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(AuthErrorCode.USER_NOT_FOUND));

        String savedToken = refreshTokenService.get(memberId);
        if (!refreshToken.equals(savedToken)) {
            throw new BaseException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        return generateAndSaveTokens(user);
    }

    public UserResponse getMemberById(Long memberId) {
        User user = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.from(user);
    }

    public void logout(Long memberId,String accessToken) {
        memberRepository.findById(memberId)
                .orElseThrow(()-> new BaseException(AuthErrorCode.USER_NOT_FOUND));
        refreshTokenService.delete(memberId);
        // 리프레시 토큰 Redis에서 삭제
        refreshTokenService.delete(memberId);

        // 액세스 토큰 블랙리스트에 추가 (남은 만료시간만큼)
        long remainingMillis = jwtService.getRemainingMillis(accessToken);
        if (remainingMillis > 0) {
            blacklistService.add(accessToken, remainingMillis);
        }
    }

    private TokenResponse generateAndSaveTokens(User user) {
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId());
        refreshTokenService.save(user.getId(), refreshToken);
        return new TokenResponse(accessToken, refreshToken);
    }
}
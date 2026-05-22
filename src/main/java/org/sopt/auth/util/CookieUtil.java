package org.sopt.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {
    private static final int REFRESH_TOKEN_MAX_AGE = 1209600; // 14일

    public static void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(REFRESH_TOKEN_MAX_AGE)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

package org.sopt.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.service.BlacklistService;
import org.sopt.global.api.exception.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final BlacklistService blacklistService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(">>> Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length()).trim();
            try {
                if (blacklistService.isBlacklisted(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                Long memberId = jwtService.verifyAndGetMemberId(token);
                System.out.println(">>> 인증 성공, memberId: " + memberId);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        memberId, null, Collections.emptyList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (BaseException e) {
                System.out.println(">>> 토큰 검증 실패: " + e.getMessage());
            }
        } else {
            System.out.println(">>> Authorization 헤더 없음 또는 Bearer 형식 아님");
        }

        filterChain.doFilter(request, response);
    }
}
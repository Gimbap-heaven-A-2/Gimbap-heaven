package com.sparta.gimbap_heaven.security;


import com.sparta.gimbap_heaven.jwt.JwtUtil;
import com.sparta.gimbap_heaven.user.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.sparta.gimbap_heaven.jwt.JwtUtil.AUTHORIZATION_KEY;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getJwtFromHeader(req);


        if (StringUtils.hasText(accessTokenValue)) {
            log.info(accessTokenValue);

            try {
                if (!jwtUtil.validateToken(accessTokenValue)) {
                    log.error("유효하지않은 AccesToken");

                    res.setStatus(400);
                    res.setCharacterEncoding("utf-8");
                    PrintWriter writer = res.getWriter();
                    writer.println("AccessToken이 유효하지 않습니다.");
                    return;
                }

            } catch (ExpiredJwtException e) {
                // 만료된 accessToken 일경우 accessToken 재발급
                // 쿠키에서 리프레시 토큰가져와서 유효성 검사후  발급
                String refreshToken = jwtUtil.getTokenFromRequest(req);
//                refreshToken = jwtUtil.substringToken(refreshToken);
                log.info(refreshToken);
                // refrshToken 검증
                if (!jwtUtil.validateToken(refreshToken)) {
                    log.error("유효하지않은 RefreshToken");

                    res.setStatus(400);
                    res.setCharacterEncoding("utf-8");
                    PrintWriter writer = res.getWriter();
                    writer.println("유효하지않은 RefreshToken 입니다. 다시 로그인 해주세요.");

                    return;
                }

                // refreshToken DB조회
                if (!jwtUtil.checkTokenDB(refreshToken)) {
                    log.error("refreshtoken not exist");

                    res.setStatus(400);
                    res.setCharacterEncoding("utf-8");
                    PrintWriter writer = res.getWriter();
                    writer.println("등록되지않은 RefreshToken 입니다. 다시 로그인 해주세요.");

                    return;
                }

                // accessToken 재발급
                Claims user = jwtUtil.getUserInfoFromToken(refreshToken);
                String accessToken = jwtUtil.createAccessToken(user.getSubject(), UserRoleEnum.valueOf(user.get(AUTHORIZATION_KEY).toString()));

                // AccessToken 헤더에 저장
                res.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

                res.setStatus(200);
                res.setCharacterEncoding("utf-8");
                PrintWriter writer = res.getWriter();
                writer.println("AccessToken이 재발급되었습니다. 다시 시도 해주세요.");

                return;
            }

            // 정상 동작일때
            Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
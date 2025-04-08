package code.global.security.jwt.util;

import code.global.security.domain.TokenResponse;
import code.global.security.domain.UserDetailsImpl;
import code.global.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Log4j2
@Component
public class JwtTokenProvider {

    @Value("${spring.security.jwt.access-token.expired-time}")
    private Long accessTokenExpiredTime;

    @Value("${spring.security.jwt.refresh-token.expired-time}")
    private Long refreshTokenExpiredTime;

    private final Key key;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtTokenProvider(@Value("${spring.security.jwt.secret}")String secret, UserDetailsServiceImpl userDetailsService) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
    }

    public TokenResponse createToken(Authentication authentication){

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("type", "Access")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("type", "Refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("[createToken] : Jwt Token issue complete with User's Id {}", authentication.getName());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getAccessTokenFromRequest(HttpServletRequest request){

        if(request.getHeader("Authorization") == null){
            log.error("[getAccessTokenFromRequest] : Request's \"Authorization\" header is null");
            throw new JwtException("Authorization Header is null");
        }

        return request.getHeader("Authorization")
                .substring(7);
    }

    public boolean validateAccessToken(String accessToken){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            log.info("[validateAccessToken] : Token validation complete");

            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new JwtException("토큰의 형식이 옳바르지 않습니다.");
        } catch (SignatureException | SecurityException e) {
            throw new JwtException("토큰의 서명이 옳바르지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("토큰의 형식이 만료되었습니다. ");
        }
    }

    public Authentication getAuthenticationFromAccessToken(String accessToken){
            UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(getLoginIdFromAccessToken(accessToken));

            log.info("[getAuthenticationFromAccessToken] : Get Authentication From Access Token complete with {}", userDetails.getUsername());

            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getLoginIdFromAccessToken(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }
}
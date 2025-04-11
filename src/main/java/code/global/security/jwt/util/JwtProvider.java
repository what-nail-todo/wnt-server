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
public class JwtProvider {

    @Value("${spring.security.jwt.access-token.expired-time}")
    private long accessTokenExpiredTime;

    @Value("${spring.security.jwt.refresh-token.expired-time}")
    private long refreshTokenExpiredTime;

    private final Key key;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtProvider(@Value("${spring.security.jwt.secret}")String secret, UserDetailsServiceImpl userDetailsService) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
    }

    public TokenResponse createToken(String email){

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("type", "Access")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("[createToken] : Jwt Token issue complete with User's Id {}", email);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .expiredTime(accessTokenExpiredTime)
                .build();
    }

    public String getAccessTokenFromRequest(HttpServletRequest request){

        return CookieUtil.getTokenFromRequest(request);
    }

    public boolean validateAccessToken(String accessToken){

        if (accessToken == null)
            throw new JwtException("토큰이 비어있습니다");

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
            UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(getEmailFromAccessToken(accessToken));

            log.info("[getAuthenticationFromAccessToken] : Get Authentication From Access Token complete with {}", userDetails.getUsername());

            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailFromAccessToken(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }
}
package code.global.security.jwt.filter;

import code.global.security.jwt.util.JwtProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtProvider jwtProvider;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final List<String> whiteListUri = Arrays.asList(
            "/swagger-ui/**", "/v3/api-docs/**",
            "/api/v1/auth/email/**",
            "/api/v1/auth/sign-up/normal",
            "/api/v1/auth/sign-up/social",
            "/api/v1/auth/sign-in"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return whiteListUri.stream()
                .anyMatch(uri -> antPathMatcher.match(uri, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
        try{
            String token = jwtProvider.getAccessTokenFromRequest(request);

            if(jwtProvider.validateAccessToken(token)){
                Authentication authentication = jwtProvider.getAuthenticationFromAccessToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }catch (JwtException e){
            throw e;
        }
    }
}

package code.global.security.jwt.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static ResponseCookie createCookie(String name, String value, String domain, long maxAge){
        return ResponseCookie.from(name, value)
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(maxAge)
                .build();
    }

    public static String getTokenFromRequest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("accessToken"))
                    return cookie.getValue();
            }
        }

        return null;
    }
}

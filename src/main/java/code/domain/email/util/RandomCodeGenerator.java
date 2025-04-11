package code.domain.email.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeGenerator {

    private static final String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom random = new SecureRandom();

    public static String generate(){
        StringBuilder code = new StringBuilder(6);

        for(int i = 0; i < 6; i++){
            code.append(characters.charAt(random.nextInt(characters.length())));
        }

        return code.toString();
    }
}

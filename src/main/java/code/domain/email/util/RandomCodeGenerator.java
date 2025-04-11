package code.domain.email.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeGenerator {

    @Value("${spring.mail.characters}")
    private static String characters;

    private static final SecureRandom random = new SecureRandom();

    public static String generate(){
        StringBuilder code = new StringBuilder(6);

        for(int i = 0; i < 6; i++){
            code.append(code.charAt(random.nextInt(characters.length())));
        }

        return code.toString();
    }
}

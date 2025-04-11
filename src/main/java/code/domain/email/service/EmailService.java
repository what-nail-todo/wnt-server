package code.domain.email.service;

import code.domain.email.entity.EmailType;
import code.global.exception.entity.CustomErrorCode;
import code.global.exception.entity.RestApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationEmail(String email, String authCode){

        EmailType emailType = EmailType.SIGNUP;

        String subject = emailType.getSubject();
        String content = emailType.getContent(authCode);

        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);

            log.info("[ sendVerificationEmail() ] : 인증 메일 전송 성공 {}", email);

        } catch (MessagingException e){
            throw new RestApiException(CustomErrorCode.SEND_MAIL_FAILED);
        }
    }
}

package code.domain.email.entity;

import lombok.Getter;

@Getter
public enum EmailType {
    SIGNUP(
            "회원 가입을 위한 이메일입니다!",
            "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "    <meta charset='UTF-8'>" +
                    "    <title>네일뭐해? 인증 메일</title>" +
                    "    <style>" +
                    "        body { font-family: 'Helvetica', 'Arial', sans-serif; background-color: #f2f2f2; margin: 0; padding: 0; }" +
                    "        .container { max-width: 600px; margin: 40px auto; background-color: #fff; padding: 20px; border: 1px solid #ddd; border-radius: 4px; }" +
                    "        .header { text-align: center; color: #333; }" +
                    "        .content { font-size: 16px; color: #555; line-height: 1.5; }" +
                    "        .code { display: block; text-align: center; margin: 20px auto; padding: 10px; width: fit-content; border: 1px dashed #aaa; font-size: 24px; color: #000; font-weight: bold; }" +
                    "        .footer { text-align: center; font-size: 12px; color: #999; margin-top: 20px; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <h2 class='header'>네일뭐해?</h2>" +
                    "        <p class='content'>안녕하세요! 저희 네일뭐해? 서비스를 이용해주셔서 감사합니다.</p>" +
                    "        <p class='content'>회원 가입 절차를 위해 아래의 인증번호를 입력해 주세요.</p>" +
                    "        <div class='code'>{code}</div>" +
                    "        <p class='footer'>본 메일은 발신 전용입니다. 문의사항은 고객센터를 이용해 주세요.</p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>"
    );

    private final String subject;
    private final String content;

    EmailType(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getContent(String code) {
        return content.replace("{code}", code);
    }
}

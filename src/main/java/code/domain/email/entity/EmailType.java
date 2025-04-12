package code.domain.email.entity;

import lombok.Getter;

@Getter
public enum EmailType {
    SIGNUP(
            "회원 가입을 위한 이메일입니다!",
            "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "    <meta charset='UTF-8'>"
                    + "    <title>네일뭐해? 인증 메일</title>"
                    + "</head>"
                    + "<body style='margin:0; padding:0; background-color:#f2f2f2;'>"
                    + "    <table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color:#f2f2f2;'>"
                    + "        <tr>"
                    + "            <td align='center' style='padding:40px 0;'>"
                    + "                <!-- 메일 컨테이너 시작 -->"
                    + "                <table border='0' cellpadding='0' cellspacing='0' width='600' style='background-color:#ffffff; border:1px solid #ddd; border-radius:4px;'>"
                    + "                    <tr>"
                    + "                        <td align='center' style='padding: 20px;'>"
                    + "                            <h2 style=\"color:#333; margin:0; font-family:'Helvetica','Arial',sans-serif;\">네일뭐해?</h2>"
                    + "                        </td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td align='center' style=\"padding: 20px; font-family:'Helvetica','Arial',sans-serif; font-size:16px; color:#555; line-height:1.5;\">"
                    + "                            안녕하세요! 저희 네일뭐해? 서비스를 이용해주셔서 감사합니다.<br/><br/>"
                    + "                            회원 가입 절차를 위해 아래의 인증번호를 입력해 주세요."
                    + "                        </td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td align='center' style='padding: 20px;'>"
                    + "                            <div style='border:1px dashed #aaa; display:inline-block; padding:10px; font-size:24px; color:#000; font-weight:bold;'>"
                    + "                                {code}"
                    + "                            </div>"
                    + "                        </td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td align='center' style='padding: 20px;'>"
                    + "                            <p style=\"font-family:'Helvetica','Arial',sans-serif; font-size:12px; color:#999; margin:0;\">"
                    + "                                본 메일은 발신 전용입니다. 문의사항은 고객센터를 이용해 주세요."
                    + "                            </p>"
                    + "                        </td>"
                    + "                    </tr>"
                    + "                </table>"
                    + "                <!-- 메일 컨테이너 끝 -->"
                    + "            </td>"
                    + "        </tr>"
                    + "    </table>"
                    + "</body>"
                    + "</html>"
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
package code.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "content_chat")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_chat_id", length = 30)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendingTime;

    @Column(nullable = false, name = "sender_id", length = 30)
    private Long senderId;

    @Column(nullable = false, name = "chatroom_id", length = 30)
    private Long chatroomId;

    @Builder
    private ContentChat(String content, LocalDateTime sendingTime, Long senderId, Long chatroomId) {
        this.content = content;
        this.sendingTime = sendingTime;
        this.senderId = senderId;
        this.chatroomId = chatroomId;
    }
}

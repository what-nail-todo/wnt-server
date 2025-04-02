package code.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "image_chat")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageChat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_chat_id")
    private Long id;

    @Column(nullable = false, name = "object_key")
    private String objectKey;

    @Column(nullable = false, name = "extension_type")
    private String extensionType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendingTime;

    @Column(nullable = false, name = "sender_id")
    private Long senderId;

    @Column(nullable = false, name = "chatroom_id")
    private Long chatroomId;

    @Builder
    private ImageChat(String objectKey, String extensionType, LocalDateTime sendingTime, Long senderId, Long chatroomId) {
        this.objectKey = objectKey;
        this.extensionType = extensionType;
        this.sendingTime = sendingTime;
        this.senderId = senderId;
        this.chatroomId = chatroomId;
    }
}
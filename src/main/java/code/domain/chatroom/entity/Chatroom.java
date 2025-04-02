package code.domain.chatroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "chatroom")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(nullable = false, name = "last_active_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime lastActiveDate;

    @Column(nullable = false, name = "last_active_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate lastActiveTime;

    @Column(nullable = false, name = "owner_id")
    private Long ownerId;

    @Column(nullable = false, name = "customer_id")
    private Long customerId;

    @Builder
    private Chatroom(LocalDateTime lastActiveDate, LocalDate lastActiveTime, Long ownerId, Long customerId) {
        this.lastActiveDate = lastActiveDate;
        this.lastActiveTime = lastActiveTime;
        this.ownerId = ownerId;
        this.customerId = customerId;
    }
}

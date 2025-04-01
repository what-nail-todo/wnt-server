package code.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "reservation")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "request_message")
    private String requestMessage;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "is_deposited")
    private Boolean isDeposited;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "store_id")
    private Long storeId;

    @Column(nullable = false, name = "designer_id")
    private Long designerId;

    @Column(nullable = false, name = "menu_id")
    private Long menuId;

    @Builder
    private Reservation(String requestMessage, LocalDate date, LocalDateTime time, Status status,
                        Boolean isDeposited, Long userId, Long storeId, Long designerId, Long menuId) {
        this.requestMessage = requestMessage;
        this.date = date;
        this.time = time;
        this.status = status;
        this.isDeposited = isDeposited;
        this.userId = userId;
        this.storeId = storeId;
        this.designerId = designerId;
        this.menuId = menuId;
    }
}

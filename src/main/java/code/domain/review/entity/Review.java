package code.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "review")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", length = 30)
    private Long id;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, name = "user_id", length = 30)
    private Long userId;

    @Column(nullable = false, name = "store_id", length = 30)
    private Long storeId;

    @Column(nullable = false, name = "reservation_id", length = 30)
    private Long reservationId;

    @Builder
    private Review(Double rating, String content, Long userId, Long storeId, Long reservationId) {
        this.rating = rating;
        this.content = content;
        this.userId = userId;
        this.storeId = storeId;
        this.reservationId = reservationId;
    }
}

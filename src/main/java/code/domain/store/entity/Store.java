package code.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "store")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    // TODO 매장 오픈 시간과 마감 시간 정보를 어떻게 관리할 지 속성, 로직 구성 필요

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(name = "instagram_id")
    private String instagramId;

    @Column(name = "review_count")
    private Long reviewCount = 0L;

    @Column
    private Double rating;

    @Column(nullable = false, name = "owner_id")
    private Long ownerId;

    @Builder
    private Store(String name, String location, String phoneNumber, String instagramId,
                  Long reviewCount, Double rating, Long ownerId) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.instagramId = instagramId;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.ownerId = ownerId;
    }
}

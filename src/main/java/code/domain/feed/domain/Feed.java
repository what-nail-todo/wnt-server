package code.domain.feed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "feed")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Column(nullable = false, name = "image_object_key")
    private String ImageObjectKey;

    @Column(nullable = false, name = "store_id")
    private Long storeId;

    @Column(nullable = false, name = "hashtags")
    private String hashtags;

    @Builder
    private Feed(String imageObjectKey, Long storeId, String hashtags) {
        ImageObjectKey = imageObjectKey;
        this.storeId = storeId;
        this.hashtags = hashtags;
    }
}

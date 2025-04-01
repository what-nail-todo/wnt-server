package code.domain.feed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "feed")
@Entity
@Getter
@AllArgsConstructor
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
}

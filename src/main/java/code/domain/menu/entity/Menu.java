package code.domain.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "menu")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", length = 30)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, length = 20)
    private Long price;

    @Column(nullable = false, name = "image_object_key", length = 100)
    private String imageObjectKey;

    @Column(nullable = false, name = "is_representative")
    private boolean isRepresentative;

    @Column(nullable = false, name = "store_id", length = 30)
    private Long storeId;

    @Builder
    private Menu(String name, String description, Long price, String imageObjectKey, boolean isRepresentative, Long storeId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageObjectKey = imageObjectKey;
        this.isRepresentative = isRepresentative;
        this.storeId = storeId;
    }
}

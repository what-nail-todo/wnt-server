package code.domain.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "menu")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, name = "image_object_key")
    private String imageObjectKey;

    @Column(nullable = false, name = "is_representative")
    private boolean isRepresentative;

    @Column(nullable = false, name = "store_id")
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

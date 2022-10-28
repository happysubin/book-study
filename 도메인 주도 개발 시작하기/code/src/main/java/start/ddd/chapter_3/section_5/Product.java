package start.ddd.chapter_3.section_5;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId //복합 키 지원
    private ProductId id; //Serializable를 구현해야 복합키가 지원되는 듯

    @ElementCollection
    @CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds;
}

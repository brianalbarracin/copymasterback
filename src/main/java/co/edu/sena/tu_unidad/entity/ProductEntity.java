package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImageEntity> images;

    @OneToMany(mappedBy = "product")
    private List<CartItemEntity> cartItems;

    @OneToMany(mappedBy = "product")
    private List<OrderItemEntity> orderItems;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews;
}

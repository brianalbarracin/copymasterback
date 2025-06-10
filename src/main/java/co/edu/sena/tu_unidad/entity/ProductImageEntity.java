package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_images")
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "image_url")
    private String imageUrl;
}

package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "_order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    private Integer quantity;
    private Double price;
}
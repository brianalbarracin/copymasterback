package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    private String status;
    private Double total;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private PaymentEntity payment;
}

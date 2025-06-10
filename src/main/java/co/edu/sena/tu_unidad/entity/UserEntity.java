package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;


    @Column(name = "cedula")
    private String cedula;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviews;


}


package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "_addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "address_line")
    private String addressLine;
    private String city;
    private String country;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;


    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "is_default")
    private boolean isDefault;

    @OneToMany(mappedBy = "address")
    private List<OrderEntity> orders;
}


package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "states")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}


// 
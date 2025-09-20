package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByLocationId(Long locationId);
}


// 
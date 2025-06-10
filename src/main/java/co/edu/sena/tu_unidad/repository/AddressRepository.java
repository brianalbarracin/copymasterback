package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByUserId(Long userId);
}

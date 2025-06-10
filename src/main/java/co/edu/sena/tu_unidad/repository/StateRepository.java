package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StateRepository extends JpaRepository<StateEntity, Long> {
}

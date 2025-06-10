package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
    List<CategoryEntity> findByActiveTrue();
}

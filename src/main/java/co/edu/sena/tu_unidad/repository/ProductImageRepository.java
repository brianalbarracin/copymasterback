package co.edu.sena.tu_unidad.repository;
import co.edu.sena.tu_unidad.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductId(Long productId);
}

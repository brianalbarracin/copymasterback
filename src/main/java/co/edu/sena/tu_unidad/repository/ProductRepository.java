package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<ProductEntity> searchProducts(String query);
}


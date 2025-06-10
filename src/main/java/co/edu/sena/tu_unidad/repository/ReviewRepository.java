package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByProductId(Long productId);
    Optional<ReviewEntity> findByUserIdAndProductId(Long userId, Long productId);
}

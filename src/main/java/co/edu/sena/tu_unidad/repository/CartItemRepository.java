package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUserId(Long userId);
    Optional<CartItemEntity> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserId(Long userId);
}

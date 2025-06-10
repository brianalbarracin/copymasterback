package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrderId(Long orderId);
}

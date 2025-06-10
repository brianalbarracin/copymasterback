package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.SaleDetailEntity;
import co.edu.sena.tu_unidad.dto.ProductReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetailEntity, Long> {

    @Query("SELECT new co.edu.sena.tu_unidad.dto.ProductReportDto(p.name, SUM(sd.quantity), SUM(sd.quantity * p.price)) " +
            "FROM SaleDetailEntity sd JOIN sd.product p " +
            "GROUP BY p.name " +
            "ORDER BY SUM(sd.quantity) DESC")
    List<ProductReportDto> findMostSoldProducts();

    @Query("SELECT new co.edu.sena.tu_unidad.dto.ProductReportDto(p.name, SUM(sd.quantity), SUM(sd.quantity * p.price)) " +
            "FROM SaleDetailEntity sd JOIN sd.product p " +
            "GROUP BY p.name " +
            "ORDER BY SUM(sd.quantity * p.price) DESC")
    List<ProductReportDto> findProductsByRevenue();

}

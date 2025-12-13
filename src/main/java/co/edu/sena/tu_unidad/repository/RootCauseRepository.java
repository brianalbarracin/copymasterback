package co.edu.sena.tu_unidad.repository;
import co.edu.sena.tu_unidad.entity.RootCauseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface RootCauseRepository extends JpaRepository<RootCauseEntity, Long> {
    List<RootCauseEntity> findAllByOrderByCategoryAsc();
    List<RootCauseEntity> findByCategoryOrderByNameAsc(String category);
    @Query("SELECT DISTINCT r.category FROM RootCauseEntity r ORDER BY r.category ASC")
    List<String> findDistinctCategory();
}

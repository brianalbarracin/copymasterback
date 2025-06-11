package co.edu.sena.tu_unidad.dto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductReportDto {

    private String name;
    private Long quantitySold;
    private Double totalRevenue;
}

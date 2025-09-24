package co.edu.sena.tu_unidad.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitPartDto {
    private Long id;
    private Long serviceVisitId;
    private Long partId;
    private Integer qty;
    private String serialNumber;
    private BigDecimal cost;
    private String name;
    private String sku;
}

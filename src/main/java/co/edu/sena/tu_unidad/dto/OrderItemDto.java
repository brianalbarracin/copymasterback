package co.edu.sena.tu_unidad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String imageUrl;
}


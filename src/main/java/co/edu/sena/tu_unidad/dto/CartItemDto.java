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
public class CartItemDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private String imageUrl;
}


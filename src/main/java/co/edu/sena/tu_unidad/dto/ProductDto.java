package co.edu.sena.tu_unidad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private List<String> imageUrls;
}


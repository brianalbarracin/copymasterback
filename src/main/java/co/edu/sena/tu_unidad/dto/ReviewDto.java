package co.edu.sena.tu_unidad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private Integer rating;
    private String comment;
    private Date createdAt;
}

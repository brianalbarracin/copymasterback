package co.edu.sena.tu_unidad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long id;
    private Long userId;
    private Long addressId;
    private String status;
    private Double total;
    private Date createdAt;
    private List<OrderItemDto> items;
    private PaymentDto payment;
}

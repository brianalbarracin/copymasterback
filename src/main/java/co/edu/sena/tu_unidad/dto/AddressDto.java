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
public class AddressDto {
    private Long id;
    private Long userId;
    private String addressLine;
    private String city;
    private Long stateId;
    private String phone;
    private String country;
    private String zipCode;
    private boolean defaultAddress;
}

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
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String cedula;
    private String addressLine;
    private String city;
    private String zipCode;
    private Long stateId;
}


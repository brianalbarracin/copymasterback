package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDto {
    private Long id;
    private String identification;
    private String fullName;
    private String phone;
    private String email;
    private Boolean active;
}


// 
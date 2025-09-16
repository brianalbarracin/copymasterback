package co.edu.sena.tu_unidad.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String nit;
    private String name;
    private String contactName;
    private String phone;
    private String email;
    private String address;
}

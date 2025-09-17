package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private Long id;
    private String name;
    private String address;
    private String description;
}

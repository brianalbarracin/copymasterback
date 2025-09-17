package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {
    private Long id;
    private String companySerial;
    private String companyNumber;
    private String model;
    private String brand;
    private Integer year;
    private Long currentLocationId;
    private String status;
    private Long currentCustomerId;
    private Long initialReading;   // lectura inicial
    private String readingNotes;
    private String notes;

}


// 
package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.MachineDto;

import java.util.List;

public interface MachineService {
    List<MachineDto> getAllMachines();
    MachineDto getMachineById(Long id);
    MachineDto createMachine(MachineDto dto);
    MachineDto updateMachine(Long id, MachineDto dto);
    void deleteMachine(Long id);
    List<MachineDto> searchByModel(String model);
    MachineDto findByCompanySerial(String serial);
}


// 
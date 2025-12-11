package co.edu.sena.tu_unidad.service;
import co.edu.sena.tu_unidad.dto.ReadingTonerDto;
import java.util.List;

public interface ReadingTonerService {

    List<ReadingTonerDto> getReadingsByMachine(Long machineId);
    ReadingTonerDto createReadingToner(ReadingTonerDto dto);
    ReadingTonerDto updateReadingToner(Long id, ReadingTonerDto dto);
    void deleteReadingToner(Long id);
    ReadingTonerDto getReadingTonerById(Long id);
}

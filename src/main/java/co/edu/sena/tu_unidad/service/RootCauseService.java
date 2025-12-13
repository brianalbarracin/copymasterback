package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.RootCauseDto;
import java.util.List;
import java.util.Map;

public interface RootCauseService {

    List<RootCauseDto> getAllRootCauses();
    List<String> getAllCategories();
    List<RootCauseDto> getRootCausesByCategory(String category);
    Map<String, List<RootCauseDto>> getRootCausesGroupedByCategory();
    RootCauseDto getRootCauseById(Long id);

}

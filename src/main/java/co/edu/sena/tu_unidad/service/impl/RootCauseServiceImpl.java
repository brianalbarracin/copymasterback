package co.edu.sena.tu_unidad.service.impl;
import co.edu.sena.tu_unidad.dto.RootCauseDto;
import co.edu.sena.tu_unidad.entity.RootCauseEntity;
import co.edu.sena.tu_unidad.repository.RootCauseRepository;
import co.edu.sena.tu_unidad.service.RootCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RootCauseServiceImpl implements RootCauseService {
    @Autowired
    private RootCauseRepository repository;

    private RootCauseDto toDto(RootCauseEntity entity) {
        if (entity == null) return null;
        return RootCauseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .build();
    }

    @Override
    public List<RootCauseDto> getAllRootCauses() {
        return repository.findAllByOrderByCategoryAsc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCategories() {
        return repository.findDistinctCategory();
    }

    @Override
    public List<RootCauseDto> getRootCausesByCategory(String category) {
        return repository.findByCategoryOrderByNameAsc(category)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<RootCauseDto>> getRootCausesGroupedByCategory() {
        List<RootCauseDto> all = getAllRootCauses();
        return all.stream()
                .collect(Collectors.groupingBy(
                        RootCauseDto::getCategory,
                        Collectors.toList()
                ));
    }

    @Override
    public RootCauseDto getRootCauseById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

}

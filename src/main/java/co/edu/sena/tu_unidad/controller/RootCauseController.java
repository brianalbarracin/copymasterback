package co.edu.sena.tu_unidad.controller;
import co.edu.sena.tu_unidad.dto.RootCauseDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.RootCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/root-causes")

public class RootCauseController {

    @Autowired
    private RootCauseService rootCauseService;

    @GetMapping
    public ServerResponseDto getAll() {
        List<RootCauseDto> list = rootCauseService.getAllRootCauses();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Causas raíz obtenidas")
                .data(list)
                .build();
    }

    @GetMapping("/categories")
    public ServerResponseDto getCategories() {
        List<String> categories = rootCauseService.getAllCategories();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Categorías obtenidas")
                .data(categories)
                .build();
    }

    @GetMapping("/by-category")
    public ServerResponseDto getByCategory(@RequestParam String category) {
        List<RootCauseDto> list = rootCauseService.getRootCausesByCategory(category);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Causas raíz por categoría")
                .data(list)
                .build();
    }

    @GetMapping("/grouped")
    public ServerResponseDto getGrouped() {
        Map<String, List<RootCauseDto>> grouped = rootCauseService.getRootCausesGroupedByCategory();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Causas raíz agrupadas por categoría")
                .data(grouped)
                .build();
    }
}

package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.VisitPartDto;
import co.edu.sena.tu_unidad.service.VisitPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit-parts")
@RequiredArgsConstructor
public class VisitPartController {

    private final VisitPartService visitPartService;

    @PostMapping
    public ResponseEntity<VisitPartDto> create(@RequestBody VisitPartDto dto) {
        return ResponseEntity.ok(visitPartService.create(dto));
    }

    @GetMapping("/visit/{visitId}")
    public ResponseEntity<List<VisitPartDto>> getByServiceVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(visitPartService.getByServiceVisit(visitId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visitPartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

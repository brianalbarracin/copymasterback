package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.entity.LocationEntity;
import co.edu.sena.tu_unidad.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationRepository repo;

    @PostMapping
    public LocationEntity create(@RequestBody LocationEntity location) {
        return repo.save(location);
    }

    @GetMapping
    public List<LocationEntity> getAll() {
        return repo.findAll();
    }
}

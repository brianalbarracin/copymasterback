package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.entity.StateEntity;
import co.edu.sena.tu_unidad.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public ServerResponseDto getAllStates() {
        List<StateEntity> states = stateService.getAllStates();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("States retrieved successfully")
                .data(states)
                .build();
    }
}

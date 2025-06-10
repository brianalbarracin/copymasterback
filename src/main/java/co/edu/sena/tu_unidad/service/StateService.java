package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.entity.StateEntity;
import co.edu.sena.tu_unidad.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<StateEntity> getAllStates() {
        return stateRepository.findAll();
    }
}

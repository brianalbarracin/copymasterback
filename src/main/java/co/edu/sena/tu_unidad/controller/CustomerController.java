package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.CustomerDto;
import co.edu.sena.tu_unidad.entity.ServiceRequestEntity;
import co.edu.sena.tu_unidad.service.CustomerService;
import co.edu.sena.tu_unidad.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*") // habilita llamadas desde tu front
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ServiceRequestService requestService;

    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message, Object data) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", status);
        resp.put("message", message);
        resp.put("data", data);
        return ResponseEntity.status(status).body(resp);
    }

    // 🔹 Listar todos los clientes
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return buildResponse(200, "Clientes obtenidos", customers);
    }

    // 🔹 Obtener un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        CustomerDto c = customerService.getCustomerById(id);
        if (c == null) {
            return buildResponse(404, "Cliente no encontrado", null);
        }
        return buildResponse(200, "Cliente obtenido", c);
    }

    // 🔹 Crear cliente
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody CustomerDto dto) {
        CustomerDto c = customerService.createCustomer(dto);
        return buildResponse(201, "Cliente creado", c);
    }

    // 🔹 Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody CustomerDto dto) {
        CustomerDto c = customerService.updateCustomer(id, dto);
        if (c == null) {
            return buildResponse(404, "Cliente no encontrado", null);
        }
        return buildResponse(200, "Cliente actualizado", c);
    }

    // 🔹 Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean ok = customerService.deleteCustomer(id);
        if (!ok) {
            return buildResponse(404, "Cliente no encontrado", null);
        }
        return buildResponse(200, "Cliente eliminado", true);
    }

    // 🔹 Obtener solicitudes asociadas a un cliente
    @GetMapping("/{id}/requests")
    public ResponseEntity<Map<String, Object>> getRequests(@PathVariable Long id) {
        List<ServiceRequestEntity> requests = requestService.getRequestsByCustomer(id);
        return buildResponse(200, "Solicitudes del cliente obtenidas", requests);
    }
}

package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.CustomerDto;
import co.edu.sena.tu_unidad.entity.CustomerEntity;
import co.edu.sena.tu_unidad.repository.CustomerRepository;
import co.edu.sena.tu_unidad.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.sena.tu_unidad.entity.LocationEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repo;

    private CustomerDto toDto(CustomerEntity e) {
        if (e == null) return null;
        return CustomerDto.builder()
                .id(e.getId())
                .nit(e.getNit())
                .name(e.getName())
                .contactName(e.getContactName())
                .phone(e.getPhone())
                .email(e.getEmail())
                .address(e.getAddress())
                .build();
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return repo.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {

        CustomerEntity e = new CustomerEntity();
        e.setNit(dto.getNit());
        e.setName(dto.getName());
        e.setContactName(dto.getContactName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        e.setAddress(dto.getAddress());
        e.setCreatedAt(OffsetDateTime.now());
        // Crear la Location automáticamente
        LocationEntity location = LocationEntity.builder()
                .name(dto.getName()) // mismo nombre del cliente
                .address(dto.getAddress())
                .description("Ubicación principal de " + dto.getName())
                .build();

        e.setLocation(location);

        CustomerEntity saved = repo.save(e);
        return toDto(e);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        CustomerEntity e = repo.findById(id).orElse(null);
        if (e == null) return null;
        e.setNit(dto.getNit());
        e.setName(dto.getName());
        e.setContactName(dto.getContactName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        e.setAddress(dto.getAddress());
        repo.save(e);
        return toDto(e);
    }

    @Autowired
    private CustomerRepository repository;

    @Override
    public boolean deleteCustomer(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}

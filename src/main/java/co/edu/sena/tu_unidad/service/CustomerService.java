package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.CustomerDto;
import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomerById(Long id);
    CustomerDto createCustomer(CustomerDto dto);
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    boolean deleteCustomer(Long id);

}

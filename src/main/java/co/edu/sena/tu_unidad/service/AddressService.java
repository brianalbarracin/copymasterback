package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.AddressDto;
import co.edu.sena.tu_unidad.entity.AddressEntity;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.entity.StateEntity;
import co.edu.sena.tu_unidad.exception.NotFoundException;
import co.edu.sena.tu_unidad.repository.AddressRepository;
import co.edu.sena.tu_unidad.repository.UserRepository;
import co.edu.sena.tu_unidad.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AddressDto> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AddressDto getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    public AddressDto createAddress(Long userId, AddressDto addressDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        AddressEntity address = new AddressEntity();
        address.setUser(user);
        address.setAddressLine(addressDto.getAddressLine());
        address.setCity(addressDto.getCity());
        StateEntity state = stateRepository.findById(addressDto.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found"));

        address.setState(state);
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());

        return convertToDto(addressRepository.save(address));
    }

    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        address.setAddressLine(addressDto.getAddressLine());
        address.setCity(addressDto.getCity());
        StateEntity state = stateRepository.findById(addressDto.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found"));

        address.setState(state);
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());

        return convertToDto(addressRepository.save(address));
    }

    public boolean deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new NotFoundException("Address not found");
        }
        addressRepository.deleteById(id);
        return true;
    }

    public void setDefaultAddress(Long userId, Long addressId) {
        // Reset all addresses to non-default first
        addressRepository.findByUserId(userId).forEach(address -> {
            address.setDefault(false);
            addressRepository.save(address);
        });

        // Set the selected address as default
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        address.setDefault(true);
        addressRepository.save(address);
    }

    private AddressDto convertToDto(AddressEntity entity) {
        return AddressDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .addressLine(entity.getAddressLine())
                .city(entity.getCity())
                .stateId(entity.getState() != null ? entity.getState().getId() : null)
                .country(entity.getCountry())
                .phone(entity.getUser().getPhone())
                .zipCode(entity.getZipCode())
                .defaultAddress(entity.isDefault())
                .build();
    }
}

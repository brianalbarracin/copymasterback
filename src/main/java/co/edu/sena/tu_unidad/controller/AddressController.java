package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.AddressDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ServerResponseDto getUserAddresses(@PathVariable Long userId) {
        List<AddressDto> addresses = addressService.getUserAddresses(userId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User addresses retrieved successfully")
                .data(addresses)
                .build();
    }

    @PostMapping("/user/{userId}")
    public ServerResponseDto addAddress(
            @PathVariable Long userId,
            @RequestBody AddressDto addressDto) {
        AddressDto address = addressService.createAddress(userId, addressDto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Address added successfully")
                .data(address)
                .build();
    }

    @PutMapping("/{addressId}")
    public ServerResponseDto updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressDto addressDto) {
        AddressDto address = addressService.updateAddress(addressId, addressDto);
        return ServerResponseDto.builder()
                .status(address != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(address != null ? "Address updated successfully" : "Address not found")
                .data(address)
                .build();
    }

    @DeleteMapping("/{addressId}")
    public ServerResponseDto deleteAddress(@PathVariable Long addressId) {
        boolean deleted = addressService.deleteAddress(addressId);
        return ServerResponseDto.builder()
                .status(deleted ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(deleted ? "Address deleted successfully" : "Address not found")
                .data(deleted)
                .build();
    }
}


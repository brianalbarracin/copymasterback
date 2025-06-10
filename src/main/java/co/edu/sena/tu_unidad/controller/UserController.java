package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.UserDto;
import co.edu.sena.tu_unidad.service.UserService;
import co.edu.sena.tu_unidad.repository.UserRepository;
import co.edu.sena.tu_unidad.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ServerResponseDto getUserById(@PathVariable Long id) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User retrieved successfully")
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping("/email/{email}")
    public ServerResponseDto getUserByEmail(@PathVariable String email) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User retrieved successfully")
                .data(userService.getUserByEmail(email))
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(userService.updateUser(id, userDto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ServerResponseDto deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User deleted successfully")
                .data(null)
                .build();
    }

    @PutMapping("/{id}/password")
    public ServerResponseDto changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        boolean changed = userService.changePassword(id, oldPassword, newPassword);
        return ServerResponseDto.builder()
                .status(changed ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value())
                .message(changed ? "Password changed successfully" : "Failed to change password")
                .data(changed)
                .build();
    }
    @GetMapping
    public ServerResponseDto getAllUsers() {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Users retrieved successfully")
                .data(userService.getAllUsers())
                .build();
    }


}
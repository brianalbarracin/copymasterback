package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto register(UserDto dto);
    UserDto updateUser(Long id, UserDto dto);
    void deleteUser(Long id);
    List<UserDto> getAllUsers();
    boolean changePassword(Long id, String oldPassword, String newPassword);
}


// 
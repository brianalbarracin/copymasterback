package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.UserDto;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.UserRepository;
import co.edu.sena.tu_unidad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.OffsetDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDto toDto(UserEntity e) {
        if (e == null) return null;
        return UserDto.builder()
                .id(e.getId())
                .username(e.getUsername())
                .email(e.getEmail())
                .fullName(e.getFullName())
                .role(e.getRole())
                .phone(e.getPhone())
                .build();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toDto).orElse(null);
    }

    @Override
    public UserDto register(UserDto dto) {
        UserEntity e = new UserEntity();
        e.setUsername(dto.getUsername());
        e.setEmail(dto.getEmail());
        e.setFullName(dto.getFullName());
        e.setRole(dto.getRole());
        e.setPhone(dto.getPhone());
        e.setPassword(passwordEncoder.encode("default123")); // default password, change later
        e.setCreatedAt(OffsetDateTime.now());
        e = userRepository.save(e);
        return toDto(e);
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        UserEntity e = userRepository.findById(id).orElse(null);
        if (e == null) return null;
        e.setFullName(dto.getFullName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        e.setRole(dto.getRole());
        userRepository.save(e);
        return toDto(e);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        UserEntity e = userRepository.findById(id).orElse(null);
        if (e == null) return false;
        if (!passwordEncoder.matches(oldPassword, e.getPassword())) return false;
        e.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(e);
        return true;
    }
}


// 
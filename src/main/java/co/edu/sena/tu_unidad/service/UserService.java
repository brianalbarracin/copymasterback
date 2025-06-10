package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.UserDto;
import co.edu.sena.tu_unidad.dto.AddressDto;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.entity.AddressEntity;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto register(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        UserEntity saved = repository.save(entity);
        return convertToDto(saved);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<UserEntity> findById(Long id) {
        return repository.findById(id);
    }

    public UserDto getUserById(Long id) {
        return repository.findById(id)
                .map(this::convertToDto)  // si tienes un método para convertir entidad a DTO
                .orElse(null);
    }

    private UserDto convertToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setCedula(entity.getCedula());
        // No pases la contraseña al DTO por seguridad
        return dto;
    }
   /* public UserDto getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::convertToDto)
                .orElse(null);
    }*/
   public UserDto getUserByEmail(String email) {
       UserEntity user = repository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       UserDto dto = new UserDto();
       dto.setId(user.getId());
       dto.setName(user.getName());
       dto.setEmail(user.getEmail());
       dto.setPhone(user.getPhone());
       dto.setCedula(user.getCedula());

       // Toma la primera dirección si existe
       if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
           AddressEntity address = user.getAddresses().get(0); // puedes cambiar lógica si usas "dirección principal"

           dto.setAddressLine(address.getAddressLine());
           dto.setCity(address.getCity());
           dto.setZipCode(address.getZipCode());

           if (address.getState() != null) {
               dto.setStateId(address.getState().getId());
           }
       }

       return dto;
   }


    public UserDto updateUser(Long id, UserDto dto) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(dto.getName());
                    user.setEmail(dto.getEmail());
                    user.setPhone(dto.getPhone());
                    user.setCedula(dto.getCedula());
                    // Si quieres actualizar contraseña, hazlo con cuidado y codifícala
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }
                    UserEntity updatedUser = repository.save(user);
                    return convertToDto(updatedUser);
                })
                .orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<UserEntity> optionalUser = repository.findById(userId);
        if (optionalUser.isEmpty()) {
            return false;  // Usuario no encontrado
        }
        UserEntity user = optionalUser.get();

        // Verificar que la contraseña vieja coincida
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;  // Contraseña vieja incorrecta
        }

        // Actualizar con la nueva contraseña codificada
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
        return true;
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> users = repository.findAll();
        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build()).toList();
    }


}
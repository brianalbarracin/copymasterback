package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}


// 
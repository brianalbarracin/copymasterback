package co.edu.sena.tu_unidad.service;


import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.AuthorityUtils;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true, true, true, true,
                AuthorityUtils.NO_AUTHORITIES // puedes cambiar esto si usas roles
        );
    }
}

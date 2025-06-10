package co.edu.sena.tu_unidad.service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.sena.tu_unidad.entity.UserEntity;

@Service

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);

        String email = user.getAttribute("email");
        if (email == null) {
            throw new OAuth2AuthenticationException("No email found from OAuth2 provider");
        }

        // Crea el usuario si no existe
        userRepository.findByEmail(email).orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setEmail(email);
            newUser.setName(user.getAttribute("name"));
            newUser.setPassword("OAUTH"); // solo para evitar null
            return userRepository.save(newUser);
        });

        return user;
    }
}

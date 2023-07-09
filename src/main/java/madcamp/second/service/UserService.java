package madcamp.second.service;

import madcamp.second.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    void signup(User user);
    void editUser(User user);
    void withdrawProfile(Long id);
    PasswordEncoder passwordEncoder();
    String getAccessToken(String code);

    UsernamePasswordAuthenticationToken login(String email, String password);

    String generateToken(Authentication authentication);

}
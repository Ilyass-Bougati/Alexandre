package alex.server.services;

import alex.server.user.CustomUserDetails;
import alex.server.user.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import static alex.server.utils.AuthFunctions.isAuthenticated;

@Service
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public CustomUserDetails getUser(HttpSession session) {
        if (isAuthenticated(session)) {
            long userId = (long) session.getAttribute("USER_ID");
            return (CustomUserDetails) customUserDetailsService.loadUserByUsername(userId);
        } else {
            return null;
        }
    }

}

package alex.server.utils;

import alex.server.DTO.UserDTO;
import alex.server.interfaces.UserInterface;
import alex.server.security.CustomUserDetails;
import alex.server.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

public class AuthFunctions {

    public static void authenticate(HttpSession session, UserInterface user) {
        session.setAttribute("USER_ID", user.getId());
    }

    public static void logout(HttpSession session) {
        if (isAuthenticated(session)) {
            session.invalidate();
        }
    }

    public static boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("USER_ID") != null;
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}

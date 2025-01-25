package alex.server.utils;

import alex.server.DTO.UserDTO;
import alex.server.interfaces.UserInterface;
import alex.server.security.CustomUserDetails;
import alex.server.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class AuthFunctions {
    public static CustomUserDetailsService userDetailsService;

    public static void authenticate(HttpSession session, UserInterface user) {
        session.setAttribute("USER_ID", user.getId());
    }

    public static void logout(HttpSession session) {
        if (isAuthenticated(session)) {
            session.invalidate();
        }
    }

    public static CustomUserDetails getUser(HttpSession session) {

        if (session.getAttribute("USER_EMAIL") == null || session.getAttribute("USER_ID") == null) {
            return null;
        }

        String email = (String) session.getAttribute("USER_EMAIL");
        return (CustomUserDetails) userDetailsService.loadUserByUsername(email);
    }

    public static boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("USER_ID") != null;
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}

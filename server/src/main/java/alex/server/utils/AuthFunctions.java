package alex.server.utils;

import alex.server.user.UserInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

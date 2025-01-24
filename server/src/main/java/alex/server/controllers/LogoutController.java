package alex.server.controllers;

import alex.server.utils.AuthFunctions;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        AuthFunctions.logout(session);
        return ResponseEntity.ok().build();
    }

}

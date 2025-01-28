package alex.server.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    public UserController(CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getUser(HttpSession session) {
        long userId = (long) session.getAttribute("USER_ID");
        CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userId);
        return ResponseEntity.ok().body(new UserDTO(user.getUser()));
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateUser(
            HttpSession session, @RequestBody @Valid UserDTO userDTO
    ) {
        long userId = (long) session.getAttribute("USER_ID");
        try {
            User modifiedUser = new User(userDTO);
            // a second security measure
            modifiedUser.setId(userId);

            userRepository.save(modifiedUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteUser(HttpSession session) {
        long userId = (long) session.getAttribute("USER_ID");
        try {
            userRepository.deleteById(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500));
        }
    }

    // TODO : Test this
    @PutMapping("/password")
    public ResponseEntity<Void> modifyPassword(
            @RequestBody @Valid PasswordModificationRequest password,
            HttpSession session
    ) {
        long userId = (long) session.getAttribute("USER_ID");
        CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userId);

        // checking if the old password and the user password are the same
        if (BCrypt.checkpw(password.getOldPassword(), user.getPassword())) {
            user.getUser().setPassword(password.getNewPassword());
            try {
                userRepository.save(user.getUser());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(500));
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403));
        }


    }

}

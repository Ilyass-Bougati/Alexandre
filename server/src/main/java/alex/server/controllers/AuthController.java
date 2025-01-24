package alex.server.controllers;

import alex.server.DTO.RegisterRequest;
import alex.server.entities.Role;
import alex.server.entities.User;
import alex.server.repositories.RoleRepository;
import alex.server.repositories.UserRepository;
import alex.server.utils.AuthFunctions;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest registerRequest,
            HttpSession session
    ) {
        // Creating the new user
        User newUser = new User(
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        // adding the default USER role
        newUser.getRoles().add(
                new Role("USER")
        );

        // saving the user
        try {
            userRepository.save(newUser);
            // on success
            AuthFunctions.authenticate(session, newUser);
            return ResponseEntity.ok().build();

        } catch (ValidationException e) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(401),
                    "Invalid email or password"
            );

        } catch (Exception e) {
            logger.trace(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    e.getMessage()
            );
        }
    }
}

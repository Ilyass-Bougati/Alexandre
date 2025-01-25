package alex.server.controllers;

import alex.server.DTO.AuthRequest;
import alex.server.entities.Role;
import alex.server.entities.User;
import alex.server.repositories.UserRepository;
import alex.server.security.CustomUserDetails;
import alex.server.services.CustomUserDetailsService;
import alex.server.utils.AuthFunctions;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid AuthRequest authRequest,
            HttpSession session
    ) {
        // Checking if the email already exists
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(401),
                    "Email already in use"
            );
        }

        // Creating the new user
        User newUser = new User(
                authRequest.getEmail(),
                authRequest.getPassword()
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

        }
        catch (Exception e) {
            logger.trace(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    e.getMessage()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody @Valid AuthRequest authRequest,
            HttpSession session
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authRequest.getEmail());
            // checking if the passwords match
            if (AuthFunctions.checkPassword(authRequest.getPassword(), userDetails.getPassword())) {
                AuthFunctions.authenticate(session, userDetails);
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(
                        HttpStatusCode.valueOf(401),
                        "Invalid email or password"
                );
            }
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(401),
                    "Invalid email or password"
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500)
            );
        }
    }

}

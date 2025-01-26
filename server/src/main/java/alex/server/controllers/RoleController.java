package alex.server.controllers;

import alex.server.DTO.RoleDTO;
import alex.server.entities.Role;
import alex.server.repositories.RoleRepository;
import alex.server.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/role")
public class RoleController {

    private final UserRepository userRepository;

    public RoleController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<RoleDTO> getRoles(HttpSession session) {
        long userId = (long) session.getAttribute("USER_ID");
        Optional<List<Role>> roles = userRepository.findRolesById(userId);
        if (roles.isPresent()) {
            return ResponseEntity.ok(new RoleDTO(roles.get()));
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500));
        }
    }

    // TODO : add making a user a seller, admin or staff

}

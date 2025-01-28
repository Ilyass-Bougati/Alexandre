package alex.server.card;

import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import alex.server.user.User;
import alex.server.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/card")
public class CardController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public CardController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Card>> getCards(HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        return ResponseEntity.ok(user.getCards());
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCard(@RequestBody CardDTO cardDTO, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();

        assert user.getCards() != null;
        user.getCards().add(new Card(cardDTO));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}

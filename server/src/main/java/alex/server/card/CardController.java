package alex.server.card;

import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import alex.server.user.User;
import alex.server.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/card")
public class CardController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public CardController(AuthService authService, UserRepository userRepository, CardRepository cardRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<CardDTO>> getCards(HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        // generating card list
        List<CardDTO> cards = new ArrayList<>();
        assert user.getCards() != null;
        for (Card card : user.getCards()) {
            cards.add(new CardDTO(card));
        }

        return ResponseEntity.ok(cards);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCard(
            @RequestBody @Valid CardDTO cardDTO,
            HttpSession session
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();

        assert user.getCards() != null;
        user.getCards().add(new Card(cardDTO));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/")
    public ResponseEntity<Void> updateCard(
            @RequestBody @Valid CardDTO cardDTO,
            HttpSession session
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();

        assert user.getCards() != null;
        if (user.getCards().stream().anyMatch(c -> c.getId() == cardDTO.getId())) {
            user.getCards().stream().filter(c -> c.getId() == cardDTO.getId()).findFirst().ifPresent(c -> {
                c.setCardNumber(cardDTO.getCardNumber());
                c.setCvv(cardDTO.getCvv());
                c.setExpiringDate(cardDTO.getExpiringDate());
                c.setHoldersFullName(cardDTO.getHoldersFullName());
            });
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(
            HttpSession session,
            @PathVariable Long cardId
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isPresent()) {
            assert user.getCards() != null;
            user.getCards().remove(card.get());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }

}

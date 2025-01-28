package alex.server.card;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String holdersFullName;
    private String cardNumber;
    private String expiringDate;
    private String cvv;

    public Card(CardDTO cardDTO) {
        setHoldersFullName(cardDTO.getHoldersFullName());
        setCardNumber(cardDTO.getCardNumber());
        setExpiringDate(cardDTO.getExpiringDate());
        setCvv(cardDTO.getCvv());
    }
}

package alex.server.card;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private long id;
    // TODO : add some full name validation
    @NotNull private String holdersFullName;
    @NotNull private String cardNumber;
    @NotNull private String expiringDate;
    @NotNull private String cvv;

    public CardDTO(Card card) {
        setHoldersFullName(card.getHoldersFullName());
        setCardNumber(card.getCardNumber());
        setExpiringDate(card.getExpiringDate());
        setCvv(card.getCvv());
        setId(card.getId());
    }
}

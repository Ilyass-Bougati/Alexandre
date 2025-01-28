package alex.server.card;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    @NotNull private String holdersFullName;
    @NotNull private String cardNumber;
    @NotNull private String expiringDate;
    @NotNull private String cvv;
}

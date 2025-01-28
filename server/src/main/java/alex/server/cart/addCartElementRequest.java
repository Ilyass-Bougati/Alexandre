package alex.server.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addCartElementRequest {
    @NotNull private long productId;
    @Min(1) @NotNull private int quantity;
}

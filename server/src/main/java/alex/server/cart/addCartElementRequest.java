package alex.server.cart;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addCartElementRequest {
    private long id;
    private int quantity;
}

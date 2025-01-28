package alex.server.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartElementDTO {
    @NotNull private long id;
    @NotNull private long productId;
    @NotNull private int quantity;

    private double discount;
    private boolean ordered;
    private Date addedAt = new Date();

    public CartElementDTO(CartElement cartElement) {
        setOrdered(cartElement.isOrdered());
        setAddedAt(cartElement.getAddedAt());
        setQuantity(cartElement.getQuantity());
        setDiscount(cartElement.getDiscount());
        setProductId(cartElement.getProduct().getId());
        setId(cartElement.getId());
    }
}

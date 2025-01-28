package alex.server.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartElementDTO {
    private long id;
    private long productId;
    private int quantity;
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

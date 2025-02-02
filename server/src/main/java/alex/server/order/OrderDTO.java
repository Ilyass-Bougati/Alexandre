package alex.server.order;

import alex.server.cart.CartElement;
import alex.server.cart.CartElementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private List<CartElementDTO> products;
    private double price;
    private boolean isFulfilled;
    private boolean onGoing;
    private boolean wasRefunded;

    public OrderDTO(Order order) {
        List<CartElementDTO> cardElements = new ArrayList<>();
        for (CartElement element : order.getCartElements()) {
            cardElements.add(new CartElementDTO(element));
        }

        setProducts(cardElements);
        setPrice(order.getPrice());
        setId(order.getId());
        setFulfilled(order.isFulfilled());
        setOnGoing(order.isOnGoing());
        setWasRefunded(order.isWasRefunded());
    }
}

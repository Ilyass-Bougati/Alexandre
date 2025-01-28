package alex.server.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private long productId;
    private double price;
    private boolean isFulfilled;
    private boolean onGoing;
    private boolean wasRefunded;

    public OrderDTO(Order order) {
        setProductId(order.getProduct().getId());
        setPrice(order.getPrice());
        setId(order.getId());
        setFulfilled(order.isFulfilled());
        setOnGoing(order.isOnGoing());
        setWasRefunded(order.isWasRefunded());
    }
}

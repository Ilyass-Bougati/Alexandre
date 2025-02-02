package alex.server.order;


import alex.server.cart.CartElement;
import alex.server.coupon.Coupon;
import alex.server.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<CartElement> cartElements = new ArrayList<>();
    private double price;
    private boolean isFulfilled;
    private boolean onGoing;
    private boolean wasRefunded;

    @OneToMany
    private List<Coupon> coupons = new ArrayList<>();

    @CreationTimestamp
    @Column
    private Date orderedAt = new Date();

    public Order(List<CartElement> cartElements) {
        setCartElements(cartElements);

        // calculating the price
        double price = 0;
        for (CartElement cartElement : cartElements) {
            price += cartElement.getQuantity() * cartElement.getProduct().getPrice() * (1 - cartElement.getDiscount() / 100.0);
        }

        setPrice(price);
        setFulfilled(false);
        setWasRefunded(false);
        setOnGoing(false);
    }
}

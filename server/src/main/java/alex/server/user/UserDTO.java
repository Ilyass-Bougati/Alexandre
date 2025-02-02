package alex.server.user;

import alex.server.card.Card;
import alex.server.cart.CartElement;
import alex.server.cart.CartElementDTO;
import alex.server.order.Order;
import alex.server.order.OrderDTO;
import alex.server.role.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements UserInterface {
    private long id;

    // the login data
    @Email(message = "Email should be valid")
    private String email;

    // user infos
    private String firstName;
    private String lastName;
    private String phoneNumber;

    // user address
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String country;
    private Date dateCreated;
    private List<Role> roles;
    private List<Card> cards;
    private List<CartElementDTO> cart;
    private List<OrderDTO> orders;

    public UserDTO(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhoneNumber(user.getPhoneNumber());
        setFirstAddress(user.getFirstAddress());
        setSecondAddress(user.getSecondAddress());
        setCity(user.getCity());
        setCountry(user.getCountry());
        setDateCreated(user.getDateCreated());
        setRoles(user.getRoles());
        setCards(user.getCards());

        List<CartElementDTO> cartElementDTOS = new ArrayList<>();
        assert user.getCart() != null;
        for (CartElement cartElement : user.getCart()) {
            cartElementDTOS.add(new CartElementDTO(cartElement));
        }
        setCart(cartElementDTOS);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        assert user.getOrders() != null;
        for (Order order : user.getOrders()) {
            orderDTOS.add(new OrderDTO(order));

        }
        setOrders(orderDTOS);
    }
}

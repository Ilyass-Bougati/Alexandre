package alex.server.user;

import alex.server.card.Card;
import alex.server.cart.CartElement;
import alex.server.order.Order;
import alex.server.product.Product;
import alex.server.role.Role;
import alex.server.seller.SellerInfo;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // the login data
    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;
    private String password;

    // user infos
    private String firstName;
    private String lastName;
    private String phoneNumber;

    // user address
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String country;

    @CreationTimestamp
    @Column
    private Date dateCreated = new Date();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<Role>();

    @ColumnDefault("null")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartElement> cart = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private SellerInfo sellerInfo;

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getAuthority());
        }
        return roleNames;
    }

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
    
    public User(UserDTO user) {
        // The password and roles cannot be modified this way
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
        setCards(user.getCards());
//        setCart(user.getCart());
//        setOrders(user.getOrders());
    }

}

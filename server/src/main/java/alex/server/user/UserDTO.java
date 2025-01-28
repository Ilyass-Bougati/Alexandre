package alex.server.user;

import alex.server.card.Card;
import alex.server.cart.CartElement;
import alex.server.order.Order;
import alex.server.role.Role;
import jakarta.validation.constraints.Email;

import java.util.Date;
import java.util.List;

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
    private List<CartElement> cart;
    private List<Order> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<CartElement> getCart() {
        return cart;
    }

    public void setCart(List<CartElement> cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public UserDTO() {

    }

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
        setCart(user.getCart());
        setOrders(user.getOrders());
    }
}

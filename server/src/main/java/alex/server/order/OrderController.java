package alex.server.order;

import alex.server.product.Product;
import alex.server.product.ProductRepository;
import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import alex.server.user.User;
import alex.server.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final AuthService authService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderController(AuthService authService, ProductRepository productRepository, UserRepository userRepository) {
        this.authService = authService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> makeOrder(@PathVariable long productId, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        Optional<Product> orderedProduct = productRepository.findById(productId);
        if (orderedProduct.isPresent()) {
            Product product = orderedProduct.get();
            if (product.isAvailable()) {
                Order order = new Order(product);
                assert user.getOrders() != null;
                user.getOrders().add(order);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404));
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getOrders(HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        List<OrderDTO> orders = new ArrayList<>();
        assert user.getOrders() != null;
        user.getOrders().forEach(order -> orders.add(new OrderDTO(order)));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable long orderId, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        assert user.getOrders() != null;
        Optional<Order> order = user.getOrders().stream().filter(c -> c.getId() == orderId).findFirst();

        if (order.isPresent()) {
            return ResponseEntity.ok(new OrderDTO(order.get()));
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable long orderId, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        assert user.getOrders() != null;
        Optional<Order> order = user.getOrders().stream().filter(c -> c.getId() == orderId).findFirst();

        if (order.isPresent()) {
            if (order.get().isOnGoing()) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Order cannot be cancelled! Call our support");
            } else {
                user.getOrders().remove(order.get());
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }

}

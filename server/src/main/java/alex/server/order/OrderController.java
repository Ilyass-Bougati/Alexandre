package alex.server.order;

import alex.server.card.Card;
import alex.server.cart.CartElement;
import alex.server.cart.CartElementRepository;
import alex.server.product.Product;
import alex.server.product.ProductRepository;
import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import alex.server.user.User;
import alex.server.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// TODO : Refactor all of this
@RestController
@RequestMapping("/v1/order")
public class OrderController {

	private final AuthService authService;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final CartElementRepository cartElementRepository;

	public OrderController(
			AuthService authService,
			ProductRepository productRepository,
			UserRepository userRepository,
			OrderRepository orderRepository, CartElementRepository cartElementRepository) {
		this.authService = authService;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.cartElementRepository = cartElementRepository;
	}

	@PostMapping("/")
	public ResponseEntity<Object> makeOrder(
		HttpSession session
	) {
		CustomUserDetails userDetails = authService.getUser(session);
		User user = userDetails.getUser();
		List<CartElement> products = user.getCart();

        assert products != null;
        if (!products.isEmpty()) {
			List<CartElement> productList = new ArrayList<>();
			List<CartElement> unavailable = new ArrayList<>();

			for (CartElement element : products) {
				if (element.getProduct().isAvailable()) {
					productList.add(element);
				} else {
					unavailable.add(element);
				}
			}

			if (unavailable.isEmpty()) {
				Order order = new Order(productList);
                assert user.getOrders() != null;
                user.getOrders().add(order);

				for (CartElement element : productList) {
					user.getCart().remove(element);
//					cartElementRepository.delete(element);
				}

				userRepository.save(user);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(500).body(unavailable);
			}

		} else {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No products on the list");
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
	public ResponseEntity<OrderDTO> getOrder(
		@PathVariable long orderId,
		HttpSession session
	) {
		CustomUserDetails userDetails = authService.getUser(session);
		User user = userDetails.getUser();
		assert user.getOrders() != null;
		Optional<Order> order = user
			.getOrders()
			.stream()
			.filter(c -> c.getId() == orderId)
			.findFirst();

		if (order.isPresent()) {
			return ResponseEntity.ok(new OrderDTO(order.get()));
		} else {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404));
		}
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> cancelOrder(
		@PathVariable long orderId,
		HttpSession session
	) {
		CustomUserDetails userDetails = authService.getUser(session);
		User user = userDetails.getUser();
		assert user.getOrders() != null;
		Optional<Order> order = user
			.getOrders()
			.stream()
			.filter(c -> c.getId() == orderId)
			.findFirst();

		if (order.isPresent()) {
			if (order.get().isOnGoing()) {
				throw new ResponseStatusException(
					HttpStatusCode.valueOf(401),
					"Order cannot be cancelled! Call our support"
				);
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

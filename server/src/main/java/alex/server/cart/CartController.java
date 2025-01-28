package alex.server.cart;

import alex.server.product.Product;
import alex.server.user.User;
import alex.server.product.ProductRepository;
import alex.server.user.UserRepository;
import alex.server.user.CustomUserDetails;
import alex.server.services.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final AuthService authService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartElementRepository cartElementRepository;

    public CartController(AuthService authService, ProductRepository productRepository, UserRepository userRepository, CartElementRepository cartElementRepository) {
        this.authService = authService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartElementRepository = cartElementRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCart(
            @RequestBody @Valid addCartElementRequest request,
            HttpSession session) {
        // creating the cardElement
        Optional<Product> addedProduct = productRepository.findById(request.getProductId());
        if (addedProduct.isPresent()) {
            CartElement cartElement = new CartElement();
            cartElement.setProduct(addedProduct.get());
            cartElement.setQuantity(request.getQuantity());
            cartElement.setDiscount(addedProduct.get().getDiscount());

            CustomUserDetails userDetails = authService.getUser(session);
            User user = userDetails.getUser();
            if (user.getCart() == null) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(500));
            } else {
                user.getCart().add(cartElement);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }

        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<CartElementDTO>> getCart(HttpSession session) {
        CustomUserDetails user = authService.getUser(session);

        List<CartElementDTO> cartDTO  = new ArrayList<>();
        assert user.getUser().getCart() != null;
        for (CartElement cartElement : user.getUser().getCart()) {
            cartDTO.add(new CartElementDTO(cartElement));
        }

        return ResponseEntity.ok(cartDTO);
    }


    @PutMapping("/")
    public ResponseEntity<Void> updateCart(
            @RequestBody @Valid CartElementDTO request,
            HttpSession session
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();

        // checkign that the cartElement is in the cart of the user
        assert user.getCart() != null;
        if (user.getCart().stream().anyMatch(c -> c.getId() == request.getId())) {
            user.getCart().stream().filter(c -> c.getId() == request.getId()).forEach(
                    c -> {
                        c.setQuantity(request.getQuantity());
                        c.setOrdered(request.isOrdered());
                    }
            );

            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        }
    }

    @DeleteMapping("/{cartElementId}")
    public ResponseEntity<Void> deleteCartElement(@PathVariable long cartElementId, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        Optional<CartElement> cart = cartElementRepository.findById(cartElementId);
        if (cart.isPresent()) {
            assert user.getCart() != null;
            user.getCart().remove(cart.get());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
    }
}

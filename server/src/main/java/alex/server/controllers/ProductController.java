package alex.server.controllers;

import alex.server.DTO.ProductDTO;
import alex.server.entities.Order;
import alex.server.entities.Product;
import alex.server.entities.Role;
import alex.server.repositories.ProductRepository;
import alex.server.repositories.UserRepository;
import alex.server.requests.ProductCreationRequest;
import alex.server.security.CustomUserDetails;
import alex.server.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ProductRepository productRepository;

    public ProductController(UserRepository userRepository, AuthService authService, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.productRepository = productRepository;
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreationRequest product, HttpSession session) {
        // checking if the user is a seller
        CustomUserDetails userDetails = authService.getUser(session);

        // checking if the user has the role of seller
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        } else if (!userDetails.getUser().getRoleNames().contains("SELLER")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        } else {
            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescription(product.getDescription());
            newProduct.setSeller(userDetails.getUser());

            // the product list can't be null, but it's safer to check
            if (userDetails.getUser().getProducts() == null)
            {
                throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
            }

            userDetails.getUser().getProducts().add(newProduct);
            try {
                userRepository.save(userDetails.getUser());
                return ResponseEntity.ok().body(new ProductDTO(newProduct));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Error creating product");
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            if (product.get().isAvailable())
            {
                return ResponseEntity.ok().body(new ProductDTO(product.get()));
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product does not exist");
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product does not exist");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id, HttpSession session) {
        // checking if the user is the product seller
        Optional<Product> product = productRepository.findById(id);
        CustomUserDetails user = authService.getUser(session);
        if (product.isPresent()) {
            if (product.get().getSeller().getId() == user.getUser().getId())
            {
                if (user.getUser().getProducts() != null) {
                    user.getUser().getProducts().remove(product.get());
                    userRepository.save(user.getUser());
                    return ResponseEntity.ok().build();
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Error deleting product");
                }
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody ProductDTO newProduct, HttpSession session) {
        // checking if the user is the product seller
        Optional<Product> productQueryRes = productRepository.findById(id);
        CustomUserDetails user = authService.getUser(session);

        if (productQueryRes.isPresent()) {
            Product product = productQueryRes.get();

            if (product.getSeller().getId() == user.getUser().getId()) {

                if (user.getUser().getProducts() != null) {
                    // constructing the product here
                    // Didn't want to use constructor since some field shouldn't be allowed to be altered
                    // i.e. : user_id, created_at, id
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setDescription(newProduct.getDescription());
                    product.setAvailable(newProduct.isAvailable());
                    product.setDiscount(newProduct.getDiscount());
                    productRepository.save(product);

                    return ResponseEntity.ok().build();
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Error updating product");
                }
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
            }

        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product does not exist");
        }

    }

}

package alex.server.controllers;

import alex.server.DTO.ProductDTO;
import alex.server.entities.Order;
import alex.server.entities.Product;
import alex.server.repositories.UserRepository;
import alex.server.requests.ProductCreationRequest;
import alex.server.security.CustomUserDetails;
import alex.server.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final UserRepository userRepository;
    private final AuthService authService;

    public ProductController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
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

}

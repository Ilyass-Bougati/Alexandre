package alex.server.seller;

import alex.server.role.Role;
import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import alex.server.user.User;
import alex.server.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/seller")
public class SellerController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public SellerController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<SellerInfo> getSellerInfo(HttpSession session) {
        CustomUserDetails user = authService.getUser(session);
        if (authService.isSeller(session)) {
            SellerInfo sellerInfo = user.getUser().getSellerInfo();
            return ResponseEntity.ok(sellerInfo);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "You're not a seller");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> makeSeller(
            @RequestBody @Valid BecomeSellerRequest request,
            HttpSession session
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        User user = userDetails.getUser();
        if (!authService.isSeller(session)) {
            // constructing the user infos
            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setIdentifier(request.getIdentifier());
            sellerInfo.setCompanyName(request.getCompanyName());
            user.setSellerInfo(sellerInfo);

            // giving new role
            Role role = new Role("SELLER");
            user.getRoles().add(role);

            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "You're already a seller");
        }
    }

}

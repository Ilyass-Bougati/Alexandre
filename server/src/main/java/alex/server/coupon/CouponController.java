package alex.server.coupon;

import alex.server.role.Role;
import alex.server.services.AuthService;
import alex.server.user.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/coupon")
public class CouponController {

    private final AuthService authService;
    private final CouponRepository couponRepository;

    public CouponController(AuthService authService, CouponRepository couponRepository) {
        this.authService = authService;
        this.couponRepository = couponRepository;
    }

    @PostMapping("/")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Void> createCoupon(
        @RequestBody @Valid CouponDTO couponDTO,
        HttpSession session
    ) {
        CustomUserDetails userDetails = authService.getUser(session);
        List<Role> authorities = (List<Role>) userDetails.getAuthorities();
        if (authorities.stream().anyMatch(c -> c.getAuthority().equals("ADMIN")) || authorities.stream().anyMatch(c -> c.getAuthority().equals("STAFF"))) {
            couponRepository.save(new Coupon(couponDTO));
            return ResponseEntity.ok().build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        }
    }

    @GetMapping("/")
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<Coupon>> getCoupons(HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        List<Role> authorities = (List<Role>) userDetails.getAuthorities();
        if (authorities.stream().anyMatch(c -> c.getAuthority().equals("ADMIN")) || authorities.stream().anyMatch(c -> c.getAuthority().equals("STAFF"))) {
            Iterable<Coupon> coupons = couponRepository.findAll();
            List<Coupon> couponsList = new ArrayList<>();
            coupons.forEach(couponsList::add);
            return ResponseEntity.ok(couponsList);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        }
    }

    @DeleteMapping("/{couponId}")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId, HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        List<Role> authorities = (List<Role>) userDetails.getAuthorities();
        if (authorities.stream().anyMatch(c -> c.getAuthority().equals("ADMIN")) || authorities.stream().anyMatch(c -> c.getAuthority().equals("STAFF"))) {
            Optional<Coupon> foundCoupon = couponRepository.findById(couponId);
            if (foundCoupon.isPresent()) {
                couponRepository.delete(foundCoupon.get());
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404));
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        }
    }

    @PutMapping("/")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Void> deleteCoupon(@RequestBody Coupon coupon , HttpSession session) {
        CustomUserDetails userDetails = authService.getUser(session);
        List<Role> authorities = (List<Role>) userDetails.getAuthorities();
        if (authorities.stream().anyMatch(c -> c.getAuthority().equals("ADMIN")) || authorities.stream().anyMatch(c -> c.getAuthority().equals("STAFF"))) {
            Optional<Coupon> foundCoupon = couponRepository.findById(coupon.getId());
            if (foundCoupon.isPresent()) {
                Coupon oldCoupon = foundCoupon.get();
                oldCoupon.setId(coupon.getId());
                oldCoupon.setCode(coupon.getCode());
                oldCoupon.setReduction(coupon.getReduction());
                couponRepository.save(oldCoupon);
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404));
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Unauthorized");
        }
    }
}

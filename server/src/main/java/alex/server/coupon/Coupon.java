package alex.server.coupon;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    @Min(0)
    @Max(100)
    private int reduction;

    public Coupon(CouponDTO couponDTO) {
        setCode(couponDTO.getCode());
        setReduction(couponDTO.getReduction());
    }
}

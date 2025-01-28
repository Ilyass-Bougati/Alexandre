package alex.server.coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private String code;
    @Min(0) @Max(100) private int reduction;

    public CouponDTO(Coupon coupon) {
        setCode(coupon.getCode());
        setReduction(coupon.getReduction());
    }
}

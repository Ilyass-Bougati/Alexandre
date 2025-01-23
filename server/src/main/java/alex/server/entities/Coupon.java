package alex.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Min(0)
    @Max(100)
    public int getReduction() {
        return reduction;
    }

    public void setReduction(@Min(0) @Max(100) int reduction) {
        this.reduction = reduction;
    }
}

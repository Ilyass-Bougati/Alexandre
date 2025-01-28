package alex.server.order;


import alex.server.coupon.Coupon;
import alex.server.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Product product;
    private double price;
    private boolean isFulfilled;
    private boolean wasRefunded;

    @OneToMany
    private List<Coupon> coupons = new ArrayList<>();

    @CreationTimestamp
    @Column
    private Date orderedAt = new Date();
}

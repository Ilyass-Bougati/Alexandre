package alex.server.cart;

import alex.server.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_elements")
public class CartElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Product product;
    private int quantity;
    // TODO : add order id
    @Min(0)
    @Max(100)
    private double discount;

    @CreationTimestamp
    @Column
    private Date addedAt = new Date();
    private boolean ordered;
}

package alex.server.product;

import alex.server.productPicture.ProductPicture;
import alex.server.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    @Max(100)
    @Min(0)
    private double discount;
    private boolean available = true;

    @CreationTimestamp
    @Column
    private Date addedAt = new Date();

    @OneToMany
    private List<ProductPicture> productPictures = new ArrayList<>();

    public Product(ProductDTO productDTO) {
        setName(productDTO.getName());
        setDescription(productDTO.getDescription());
        setPrice(productDTO.getPrice());
        setDiscount(productDTO.getDiscount());
        setAvailable(productDTO.isAvailable());
    }
}

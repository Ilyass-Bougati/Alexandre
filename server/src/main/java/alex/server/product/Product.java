package alex.server.product;

import alex.server.productPicture.ProductPicture;
import alex.server.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<ProductPicture> getProductPictures() {
        return productPictures;
    }

    public void setProductPictures(List<ProductPicture> productPictures) {
        this.productPictures = productPictures;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @Max(100)
    @Min(0)
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(@Max(100) @Min(0) double discount) {
        this.discount = discount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Product() {}

    public Product(ProductDTO productDTO) {
        setName(productDTO.getName());
        setDescription(productDTO.getDescription());
        setPrice(productDTO.getPrice());
        setDiscount(productDTO.getDiscount());
        setAvailable(productDTO.isAvailable());
    }
}

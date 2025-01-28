package alex.server.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// TODO : add the pictures, separate the user and the seller
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    @NotNull @NotBlank private String name;
    @NotNull @NotBlank private String description;
    @NotNull @Min(1) private double price;
    @NotNull private long seller_id;
    private double discount;
    private boolean available = true;
    private Date addedAt = new Date();


    public ProductDTO(String name, String description, double price, long seller_id, double discount, boolean available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller_id = seller_id;
        this.discount = discount;
        this.available = available;
    }

    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSeller().getId(),
                product.getDiscount(),
                product.isAvailable(),
                product.getAddedAt()
        );
    }
}

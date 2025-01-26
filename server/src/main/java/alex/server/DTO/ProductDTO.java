package alex.server.DTO;

import alex.server.entities.Product;
import alex.server.entities.User;

import java.util.Date;

// TODO : add the pictures, separate the user and the seller
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private long seller_id;
    private double discount;
    private boolean available = true;
    private Date addedAt = new Date();

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

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public ProductDTO(long id, String name, String description, double price, long seller_id, double discount, boolean available, Date addedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller_id = seller_id;
        this.discount = discount;
        this.available = available;
        this.addedAt = addedAt;
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

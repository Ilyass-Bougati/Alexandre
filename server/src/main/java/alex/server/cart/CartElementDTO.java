package alex.server.cart;

import java.util.Date;

public class CartElementDTO {
    private long id;
    private long productId;
    private int quantity;
    private double discount;
    private boolean ordered;
    private Date addedAt = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public CartElementDTO() {}

    public CartElementDTO(long id, long productId, int quantity, double discount, Date addedAt, boolean ordered) {
        setId(id);
        setProductId(productId);
        setQuantity(quantity);
        setDiscount(discount);
        setAddedAt(addedAt);
        setOrdered(ordered);
    }

    public CartElementDTO(CartElement cartElement) {
        this(
                cartElement.getId(),
                cartElement.getProduct().getId(),
                cartElement.getQuantity(),
                cartElement.getDiscount(),
                cartElement.getAddedAt(),
                cartElement.isOrdered()
        );
    }
}

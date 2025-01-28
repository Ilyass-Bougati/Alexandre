package alex.server.cart;

public class addCartElementRequest {
    private long id;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public addCartElementRequest() {}

    public addCartElementRequest(long product, int quantity) {
        this.id = product;
        this.quantity = quantity;
    }
}

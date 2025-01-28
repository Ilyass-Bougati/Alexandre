package alex.server.card;

public class CardDTO {
    private String holdersFullName;
    private String cardNumber;
    private String expiringDate;
    private String cvv;

    public String getHoldersFullName() {
        return holdersFullName;
    }

    public void setHoldersFullName(String holdersFullName) {
        this.holdersFullName = holdersFullName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(String expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public CardDTO() {}

    public CardDTO(String holdersFullName, String cardNumber, String expiringDate, String cvv) {
        this.holdersFullName = holdersFullName;
        this.cardNumber = cardNumber;
        this.expiringDate = expiringDate;
        this.cvv = cvv;
    }
}

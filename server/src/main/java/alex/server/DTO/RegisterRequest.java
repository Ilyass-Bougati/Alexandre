package alex.server.DTO;

public class RegisterRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterRequest(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public RegisterRequest() {}
}

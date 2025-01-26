package alex.server.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    @Email
    private String email;

    @Size(min = 8)
    private String password;

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Size(min = 8) String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8) String password) {
        this.password = password;
    }

    public AuthRequest(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public AuthRequest() {}
}

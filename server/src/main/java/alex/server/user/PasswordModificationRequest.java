package alex.server.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordModificationRequest {
    @NotEmpty @Size(min = 8, max = 20) private String oldPassword;
    @NotEmpty @Size(min = 8, max = 20) private String newPassword;
}

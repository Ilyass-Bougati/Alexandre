package alex.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordModificationRequest {
    private String oldPassword;
    private String newPassword;
}

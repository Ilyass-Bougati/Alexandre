package alex.server.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RoleDTO {
    private List<String> authority = new ArrayList<>();

    public RoleDTO(List<Role> roles) {
        for (Role role : roles) {
            this.authority.add(role.getAuthority());
        }
    }
}

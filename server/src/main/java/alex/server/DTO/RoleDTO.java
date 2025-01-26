package alex.server.DTO;

import alex.server.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
    private List<String> authority = new ArrayList<>();

    public List<String> getAuthorities() {
        return authority;
    }

    public void setAuthorities(List<String> authority) {
        this.authority = authority;
    }

    public RoleDTO(List<Role> roles) {
        for (Role role : roles) {
            this.authority.add(role.getAuthority());
        }
    }
}

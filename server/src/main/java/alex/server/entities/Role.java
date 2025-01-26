package alex.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

/**
 * The Role can take one of 3 values
 *  USER : which means that the person is only a buyer
 *  SELLER : which means that the person can also sell which
 *  means the user will get access to special selling tools
 *  STAFF : which means that the user is also an employed staff member
 *  ADMIN : which is self-explanatory
 *
 */

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String authority;

    public Role() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setAuthority(String roleName) {
        this.authority = roleName;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(String authority) {
        setAuthority(authority);
    }
}

package alex.server.role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(String authority) {
        setAuthority(authority);
    }

}

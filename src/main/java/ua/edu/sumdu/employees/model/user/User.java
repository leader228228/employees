package ua.edu.sumdu.employees.model.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "User")
@Table(name = "USERS")
@Getter
@Setter
@Embeddable
public class User implements UserDetails {
    @Id
    @Column(columnDefinition = "VARCHAR2(50 CHAR)")
    private String username;
    @Column(nullable = false, columnDefinition = "VARCHAR2(4000 CHAR)")
    private String password;
    @Column(nullable = false)
    private Boolean enabled;
    @OneToMany(mappedBy = "authorityID.user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities;
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void revokeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}

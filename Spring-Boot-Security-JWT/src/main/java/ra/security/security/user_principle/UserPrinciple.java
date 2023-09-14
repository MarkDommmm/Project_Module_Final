package ra.security.security.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.security.model.domain.Role;
import ra.security.model.domain.Users;


import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String name;

    private String username;
    @JsonIgnore
    private String password;
    private boolean status;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(Users user){
        List<GrantedAuthority> list =  user.getRoles().stream().map(
                role-> new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());
        return UserPrinciple.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .status(user.isStatus())
                .password(user.getPassword())
                .authorities(list).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.toyhe.app.Auth.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long userID;
    private String email;
    private String password;
    private boolean enabled;
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccessRights> modulePermissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role to GrantedAuthority
        List<GrantedAuthority> roleAuthority = new ArrayList<>(List.of(new SimpleGrantedAuthority(role.name())));

        // Convert module permissions to GrantedAuthority
        List<GrantedAuthority> moduleAuthorities = modulePermissions.stream()
                .map(permission -> new SimpleGrantedAuthority(
                        "MODEL_" + permission.getModel().getModelName().toUpperCase() + "_" +
                                (permission.isAccessRead() ? "READ" : "") +
                                (permission.isAccessWrite() ? "_WRITE" : "") +
                                (permission.isAccessUpdate() ? "_UPDATE" : "") +
                                (permission.isAccessDelete() ? "_DELETE" : "")))
                .collect(Collectors.toList());

        // Combine role and module permissions
        roleAuthority.addAll(moduleAuthorities);
        return roleAuthority;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Assume account never expires for simplicity
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Assume account never locked for simplicity
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Assume credentials never expire for simplicity
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

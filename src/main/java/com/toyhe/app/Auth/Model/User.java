package com.toyhe.app.Auth.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "app_user")
@Slf4j
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(unique = true, name = "user_name", nullable = false)
    private String email;

    private String password;

    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "user_role_assoc",
            joinColumns = @JoinColumn(name = "user_id"), // Corrected column names
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> userRoles = new ArrayList<>();  // Changed to Set to avoid duplicates

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Convert role names to GrantedAuthority
        for (UserRole role : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            // Add module permissions as authorities
            authorities.addAll(role.getModulePermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(
                            "MODEL_" + permission.getModel().getModelName().toUpperCase() + "_" +
                                    (permission.isAccessRead() ? "READ" : "") +
                                    (permission.isAccessWrite() ? "_WRITE" : "") +
                                    (permission.isAccessUpdate() ? "_UPDATE" : "") +
                                    (permission.isAccessDelete() ? "_DELETE" : "")))
                    .toList());
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
        return enabled;
    }
}

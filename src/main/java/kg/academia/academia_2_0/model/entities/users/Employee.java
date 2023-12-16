package kg.academia.academia_2_0.model.entities.users;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.enums.Gender;
import kg.academia.academia_2_0.model.enums.Level;
import kg.academia.academia_2_0.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Gender gender;
    private String phoneNumber;
    private Date dateOfBirth;
    private String username;
    private String password;
    private String position;
    private Double rating;
    private Level level;
    @ElementCollection
    private List<String> skills = new ArrayList<>();
    @ElementCollection
    private Map<String,String> socialMedias = new HashMap<>();
    private Role role;
    private Boolean active;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist()
    public void onCreate(){
        this.createDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
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
        return active;
    }
}

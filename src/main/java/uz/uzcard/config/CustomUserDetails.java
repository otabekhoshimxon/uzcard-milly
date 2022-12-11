package uz.uzcard.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.entity.ProfileEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@Component
public class CustomUserDetails implements UserDetails {


    private String id;
    private String userName;
    private String password;
    private Boolean visible;
    private GeneralRole role;
    private GeneralStatus status;

    public  CustomUserDetails() {

    }

    public CustomUserDetails(ProfileEntity base) {
        this.id=base.getId();
        this.userName=base.getUsername();
        this.role=base.getRole();
        this.status=base.getStatus();
        this.visible=base.getVisible();
        this.password=base.getPassword();
    }public CustomUserDetails(CompanyEntity base) {

        this.id=base.getId();
        this.userName=base.getUsername();
        this.role=base.getRole();
        this.status=base.getStatus();
        this.visible=base.getVisible();
        this.password=base.getPassword();
    }public CustomUserDetails(ClientEntity base) {

        this.id=base.getId();
        this.userName=base.getUsername();
        this.role=base.getRole();
        this.status=base.getStatus();
        this.visible=base.getVisible();
        this.password=base.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new LinkedList<>(Collections.singletonList(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return visible;
    }

    public String getId() {
        return id;
    }
}

package ecommerce.backend.configurations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ecommerce.backend.model.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails{

    private static final long serialVersionUID = 1L;
  
    private Integer id;
  
    private String username;
  
    @JsonIgnore
    private String password;
  
    private Collection<? extends GrantedAuthority> authorities;
  
    public UserDetailsImpl(Integer id, String email, String password,
        Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username=email;
      this.password = password;
      this.authorities = authorities;
    }
  
    public static UserDetailsImpl build(Customer user) {
       List<GrantedAuthority> authorities = new ArrayList<>(); //for now we don't have roles
  
      return new UserDetailsImpl(
          user.getId(),
          user.getEmail(),
          user.getPassword(), 
          authorities);
    }
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
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
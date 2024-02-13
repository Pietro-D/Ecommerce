package ecommerce.backend.configurations;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.model.Customer;
import ecommerce.backend.repositories.CustomerRepository;



@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private CustomerRepository customerRepository;
    
    public UserDetails loadUserByUsername(String email) throws CustomerNotExistsException{
        
        Customer user = customerRepository.findByEmail(email)
                                           .orElseThrow(() -> new CustomerNotExistsException());
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, Collections.emptyList());
    }
    
}

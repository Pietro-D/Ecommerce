package ecommerce.backend.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.model.Customer;
import ecommerce.backend.repositories.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
    @Autowired
    CustomerRepository customerRep;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws CustomerNotExistsException {
        Customer user = customerRep.findByEmail(email)
                                    .orElseThrow(() -> new CustomerNotExistsException());

        return UserDetailsImpl.build(user);
    }
}
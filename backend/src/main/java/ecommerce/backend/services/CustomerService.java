package ecommerce.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.dto.ModifyUserRequest;
import ecommerce.backend.dto.SignupRequest;
import ecommerce.backend.exceptions.CustomerAlreadyExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.model.Customer;
import ecommerce.backend.repositories.CustomerRepository;


@Service
public class CustomerService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private CustomerRepository customerRepo;

    @Transactional
    public void customerRegistration(SignupRequest customer){

        if(customerRepo.existsByEmail(customer.getEmail())) throw new CustomerAlreadyExistsException();
        
        Customer newCustomer=new Customer(customer.getName(), customer.getSurname(), 
                                            customer.getPhone(), customer.getEmail(), 
                                            encoder.encode(customer.getPassword()));
        customerRepo.save(newCustomer);
    }

    @Transactional
    public boolean removeCustomer(String email){

        Customer toRemove=customerRepo.findByEmail(email).orElse(null);
        if(toRemove==null) return false;
        customerRepo.delete(toRemove);
        return true;
    }

    @Transactional
    public void modifyCustomer(ModifyUserRequest customer, String email){

        Customer olderCustomer= customerRepo.findByEmail(email)
                                             .orElseThrow( () -> new CustomerNotExistsException());

        olderCustomer.setName(customer.getName());
        olderCustomer.setSurname(customer.getSurname());
        olderCustomer.setPhone(customer.getPhone());
    }


    
}

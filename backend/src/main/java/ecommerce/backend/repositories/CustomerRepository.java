package ecommerce.backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);


    
} 

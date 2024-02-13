package ecommerce.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.OrderElement;

@Repository
public interface OrderElementRepository extends CrudRepository<OrderElement, Integer> {

    
}
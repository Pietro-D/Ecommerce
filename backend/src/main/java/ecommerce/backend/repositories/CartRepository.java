package ecommerce.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart,Integer>{


    @Query("SELECT c.id FROM Cart c WHERE c.customerCart.email = :email")
    Optional<Integer> findIdByCustomer(@Param("email") String email);
    
    @Query("SELECT c FROM Cart c WHERE c.customerCart.email = :email")
    Optional<Cart> findCartByCustomer(@Param("email") String email);
}

package ecommerce.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.CartElement;

@Repository
public interface CartElementRepository extends CrudRepository<CartElement,Integer>{

    @Query("SELECT c FROM CartElement c WHERE c.cart.id = :id")
    List<CartElement> findAllByCart(@Param("id") Integer id);

    @Query("SELECT c FROM CartElement c "+
                    "WHERE c.cart.id = :idCart AND c.product.id = :idProd")
    Optional<CartElement> findByCartAndProduct(@Param("idCart") Integer idCart, 
                                               @Param ("idProd") Integer idProd);

}

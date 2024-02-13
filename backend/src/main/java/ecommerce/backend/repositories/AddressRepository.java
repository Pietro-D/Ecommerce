package ecommerce.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    @Query("SELECT addr FROM Address addr WHERE addr.customerAddress.email = :email AND addr.id = :id")
    Optional<Address> findByUsernameAndId(@Param("email") String email, @Param("id") Integer id);


    @Query("SELECT addr FROM Address addr WHERE addr.customerAddress.email = :email")
    List<Address> findByUsername(@Param("email") String email);

    
    @Query("SELECT EXISTS (SELECT 1 FROM Address addr " +
       "WHERE addr.customerAddress.email = :email " +
       "AND addr.addressLine = :addrLine " +
       "AND addr.postalCode = :postalCode " +
       "AND addr.country = :country " +
       "AND addr.city = :city)")
    boolean existsAddress(@Param("email") String email,
                          @Param("addrLine") String addrLine,
                          @Param("postalCode") String postalCode,
                          @Param("country") String country,
                          @Param("city") String city);
}

    

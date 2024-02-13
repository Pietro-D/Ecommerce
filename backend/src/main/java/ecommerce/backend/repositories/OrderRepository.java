package ecommerce.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Order;


@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Integer>, CrudRepository<Order,Integer>{


    @Query("SELECT o from Order o WHERE o.customerOrder.email = :email")
    Page<Order> findAllByCustomer(@Param("email") String email, Pageable p);
}
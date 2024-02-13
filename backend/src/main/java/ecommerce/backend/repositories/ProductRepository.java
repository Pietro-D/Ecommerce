package ecommerce.backend.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product,Integer> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable p);

    
    Page<Product> findByCategory_name(String name, Pageable p);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.name = :category")
    Integer countAllByCategory(@Param("category") String category);

    
    Integer countByNameContainingIgnoreCase(String name);



    
}

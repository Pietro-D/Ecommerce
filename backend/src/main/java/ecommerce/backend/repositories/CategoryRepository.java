package ecommerce.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ecommerce.backend.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer>{

    List<Category> findAll();

}

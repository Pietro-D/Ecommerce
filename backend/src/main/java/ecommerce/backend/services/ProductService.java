package ecommerce.backend.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ecommerce.backend.model.Product;
import ecommerce.backend.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRep;



    @Transactional(readOnly = true)
    public List<Product> findProduct(String name, Integer pageSize, Integer pageNumber, String sort){

         Pageable p= PageRequest.of(pageNumber,pageSize,Sort.by(sort));
         Page<Product> page=productRep.findByNameContainingIgnoreCase(name, p);
         return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Product> findProductByCategory(String category, Integer pageSize, Integer pageNumber, String sort){

         Pageable p= PageRequest.of(pageNumber,pageSize,Sort.by(sort));
         Page<Product> page=productRep.findByCategory_name(category, p);
         return page.getContent();
    }
    
}

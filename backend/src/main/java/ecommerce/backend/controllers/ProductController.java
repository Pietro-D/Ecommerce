package ecommerce.backend.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import ecommerce.backend.dto.ProductDTO;
import ecommerce.backend.model.Product;
import ecommerce.backend.repositories.ProductRepository;
import ecommerce.backend.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRep;

    @GetMapping("/{name}")
    public ResponseEntity<?> findProduct(@PathVariable String name,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                        @RequestParam(name = "sort", defaultValue = "name") String sort) {

        if(pageSize<0 || pageNumber<0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Product> result=productService.findProduct(name, pageSize, pageNumber, sort);
        List<ProductDTO> response=result.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<?> findProductByCategory(@PathVariable String category,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                    @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                    @RequestParam(name = "sort", defaultValue = "name") String sort) {

        if(pageSize<0 || pageNumber<0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Product> result=productService.findProductByCategory(category, pageSize, pageNumber, sort);
        List<ProductDTO> response=result.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{category}/total")
    public ResponseEntity<?> getTotalNumberCat(@PathVariable String category) {

        Integer res=productRep.countAllByCategory(category);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("{name}/total")
    public ResponseEntity<?> getTotalNumberSearch(@PathVariable String name) {

        Integer res=productRep.countByNameContainingIgnoreCase(name);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    

    private ProductDTO convertToDTO(Product prod){
            ProductDTO newProd=new ProductDTO();
            newProd.copyInfo(prod);
            return newProd;
    }
        

}

    

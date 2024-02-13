package ecommerce.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.backend.dto.CategoryDTO;
import ecommerce.backend.model.Category;
import ecommerce.backend.services.CategoryService;

@RestController
@RequestMapping("/categories")

public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public ResponseEntity<?> getAllCategories(){

        List<Category> categories=categoryService.getAllCategories();
        List<CategoryDTO> res=categories.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    private CategoryDTO convertToDTO(Category cat){
        return new CategoryDTO(cat.getId(), cat.getName(), cat.getIconUrl());
    }
    
}

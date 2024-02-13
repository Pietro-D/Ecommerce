package ecommerce.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.model.Category;
import ecommerce.backend.repositories.CategoryRepository;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRep;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories(){

        return categoryRep.findAll();
    }
}

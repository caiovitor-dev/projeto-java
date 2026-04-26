package com.caio.financial.service;

import com.caio.financial.entity.Category;
import com.caio.financial.entity.User;
import com.caio.financial.repository.CategoryRepository;
import com.caio.financial.repository.UserRepository;
import com.caio.financial.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SecurityService securityService;


    public Category saveCategory(Category category){

        category.setUser(securityService.getLoggedUser());
        return categoryRepository.save(category);

    }
    public Optional<Category> getCategory(UUID id){
        return  categoryRepository.findById(id);
    }
}

package com.caio.financial.controller;

import com.caio.financial.dto.CategoryCreateDTO;
import com.caio.financial.dto.CategoryResponseDTO;
import com.caio.financial.dto.UpdateCategoryDTO;
import com.caio.financial.entity.Category;
import com.caio.financial.mapper.CategoryMapper;
import com.caio.financial.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {


    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateDTO dto) {
      Category category = categoryMapper.toEntity(dto);
       categoryService.saveCategory(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable String id){

        return categoryService.getCategory(UUID.fromString(id)).map(category -> {

            CategoryResponseDTO dto = categoryMapper.toDTO(category);
            return  ResponseEntity.ok(dto);

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody UpdateCategoryDTO dto){

        return categoryService.getCategory(UUID.fromString(id)).map(category -> {

            category.setName(dto.name());
            category.setTypeCategory(dto.typeCategory());

            categoryService.saveCategory(category);

            return ResponseEntity.noContent().build();

        }).orElseGet(() ->  ResponseEntity.notFound().build());

    }


}

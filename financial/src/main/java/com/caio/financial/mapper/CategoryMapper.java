package com.caio.financial.mapper;

import com.caio.financial.dto.CategoryCreateDTO;
import com.caio.financial.dto.CategoryResponseDTO;
import com.caio.financial.dto.UpdateCategoryDTO;
import com.caio.financial.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CategoryMapper {


    public Category toEntity(CategoryCreateDTO dto);

    @Mapping(source = "id",target = "categoryId")
    public CategoryResponseDTO toDTO(Category category);

    public Category toEntity(UpdateCategoryDTO dto);
}

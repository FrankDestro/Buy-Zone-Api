package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.CategoryDTO;
import com.sysout.buy_zone_api.models.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper {

    CategoryDTO toCategoryDTO(Category category);

    Category toCategoryEntity(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);

}

package com.sysout.buy_zone_api.factory;

import com.sysout.buy_zone_api.mappers.CategoryDTOMapper;
import com.sysout.buy_zone_api.models.dto.CategoryDTO;
import com.sysout.buy_zone_api.models.entities.Category;
import com.sysout.buy_zone_api.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CategoryFactory {

    @Autowired
    private CategoryDTOMapper categoryDTOMapper;

    public static Category createCategory() {
        Set<Product> listProduct = new HashSet<>();
        listProduct.add(ProductFactory.createProduct());

        Category category = new Category(1L, "Fantasia", listProduct);
        return category;
    }

    public static CategoryDTO createCategoryDTO() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Fantasia");
        return categoryDTO;
    }
}

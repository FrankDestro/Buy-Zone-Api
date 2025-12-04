package com.sysout.buy_zone_api.services;

import com.sysout.buy_zone_api.mappers.CategoryDTOMapper;
import com.sysout.buy_zone_api.models.dto.CategoryDTO;
import com.sysout.buy_zone_api.models.entities.Category;
import com.sysout.buy_zone_api.repository.CategoryRepository;
import com.sysout.buy_zone_api.services.exceptions.DatabaseException;
import com.sysout.buy_zone_api.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> result = categoryRepository.findAll();
        return result.stream().map(cat -> categoryDTOMapper.toCategoryDTO(cat)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return categoryDTOMapper.toCategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category categoryEntity = categoryDTOMapper.toCategoryEntity(dto);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryDTOMapper.toCategoryDTO(categoryEntity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category categoryEntity = categoryRepository.getReferenceById(id);
            categoryDTOMapper.updateCategoryFromDTO(dto, categoryEntity);
            categoryEntity = categoryRepository.save(categoryEntity);
            return categoryDTOMapper.toCategoryDTO(categoryEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            categoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}

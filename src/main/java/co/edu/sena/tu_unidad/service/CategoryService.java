package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.CategoryDto;
import co.edu.sena.tu_unidad.entity.CategoryEntity;
import co.edu.sena.tu_unidad.exception.NotFoundException;
import co.edu.sena.tu_unidad.exception.DuplicateResourceException;
import co.edu.sena.tu_unidad.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(value = "categories", key = "'all'")
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "categories", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategoriesPaginated(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Cacheable(value = "category", key = "#id")
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Cacheable(value = "category", key = "#name")
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .map(this::convertToDto)
                .orElseThrow(() -> new NotFoundException("Category not found with name: " + name));
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", allEntries = true)
    })
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // Check if category name already exists
        categoryRepository.findByNameIgnoreCase(categoryDto.getName())
                .ifPresent(c -> {
                    throw new DuplicateResourceException("Category with name '" + categoryDto.getName() + "' already exists");
                });

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setActive(true);

        return convertToDto(categoryRepository.save(category));
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "category", key = "#result.name", condition = "#result != null")
    })
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        // Check if new name conflicts with other categories
        if (!category.getName().equalsIgnoreCase(categoryDto.getName())) {
            categoryRepository.findByNameIgnoreCase(categoryDto.getName())
                    .ifPresent(c -> {
                        throw new DuplicateResourceException("Category with name '" + categoryDto.getName() + "' already exists");
                    });
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        if (categoryDto.getActive() != null) {
            category.setActive(categoryDto.getActive());
        }

        return convertToDto(categoryRepository.save(category));
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id")
    })
    @Transactional
    public boolean deleteCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        // Soft delete (deactivate)
        category.setActive(false);
        categoryRepository.save(category);
        return true;  // Indica que se eliminó correctamente
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id")
    })
    @Transactional
    public CategoryDto toggleCategoryStatus(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        category.setActive(!category.isActive());
        return convertToDto(categoryRepository.save(category));
    }

    @Cacheable(value = "activeCategories", key = "'all'")
    @Transactional(readOnly = true)
    public List<CategoryDto> getActiveCategories() {
        return categoryRepository.findByActiveTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto convertToDto(CategoryEntity entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

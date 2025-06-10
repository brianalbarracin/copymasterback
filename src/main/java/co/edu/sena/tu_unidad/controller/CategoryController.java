package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.CategoryDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ServerResponseDto getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Categories retrieved successfully")
                .data(categories)
                .build();
    }

    @GetMapping("/{id}")
    public ServerResponseDto getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ServerResponseDto.builder()
                .status(category != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(category != null ? "Category retrieved successfully" : "Category not found")
                .data(category)
                .build();
    }

    @PostMapping()
    public ServerResponseDto createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Category created successfully")
                .data(category)
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.updateCategory(id, categoryDto);
        return ServerResponseDto.builder()
                .status(category != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(category != null ? "Category updated successfully" : "Category not found")
                .data(category)
                .build();
    }

    @DeleteMapping("/{id}")
    public ServerResponseDto deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        return ServerResponseDto.builder()
                .status(deleted ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(deleted ? "Category deleted successfully" : "Category not found")
                .data(deleted)
                .build();
    }
}

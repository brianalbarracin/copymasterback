package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.ProductDto;
import co.edu.sena.tu_unidad.entity.CategoryEntity;
import co.edu.sena.tu_unidad.entity.ProductEntity;
import co.edu.sena.tu_unidad.entity.ProductImageEntity;
import co.edu.sena.tu_unidad.repository.CategoryRepository;
import co.edu.sena.tu_unidad.repository.ProductImageRepository;
import co.edu.sena.tu_unidad.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String query) {
        return productRepository.searchProducts(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }



    private ProductDto convertToDto(ProductEntity entity) {
        List<String> imageUrls = productImageRepository.findByProductId(entity.getId()).stream()
                .map(ProductImageEntity::getImageUrl)
                .collect(Collectors.toList());

        Long categoryId = null;
        String categoryName = null;

        if (entity.getCategory() != null) {
            categoryId = entity.getCategory().getId();
            categoryName = entity.getCategory().getName();
        }

        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .categoryId(categoryId)
                .categoryName(categoryName)
                .imageUrls(imageUrls)
                .build();
    }


    public ProductDto createProduct(ProductDto dto) {
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductEntity product = new ProductEntity();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);

        ProductEntity savedProduct = productRepository.save(product);

        return convertToDto(savedProduct);
    }
    public ProductDto updateProduct(Long id, ProductDto dto) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setStock(dto.getStock());
        existingProduct.setCategory(category);

        ProductEntity updatedProduct = productRepository.save(existingProduct);

        return convertToDto(updatedProduct);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}

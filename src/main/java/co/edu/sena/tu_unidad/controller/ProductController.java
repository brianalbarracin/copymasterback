package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ProductDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ServerResponseDto getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .data(products)
                .build();
    }

    @GetMapping("/{id}")
    public ServerResponseDto getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ServerResponseDto.builder()
                .status(product != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(product != null ? "Product retrieved successfully" : "Product not found")
                .data(product)
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ServerResponseDto getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .data(products)
                .build();
    }

    @GetMapping("/search")
    public ServerResponseDto searchProducts(@RequestParam String query) {
        List<ProductDto> products = productService.searchProducts(query);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Search results")
                .data(products)
                .build();
    }

    @PostMapping()
    public ServerResponseDto createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .data(createdProduct)
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ServerResponseDto.builder()
                .status(updatedProduct != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(updatedProduct != null ? "Product updated successfully" : "Product not found")
                .data(updatedProduct)
                .build();
    }

    @DeleteMapping("/{id}")
    public ServerResponseDto deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return ServerResponseDto.builder()
                .status(deleted ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(deleted ? "Product deleted successfully" : "Product not found")
                .data(deleted)
                .build();
    }
}


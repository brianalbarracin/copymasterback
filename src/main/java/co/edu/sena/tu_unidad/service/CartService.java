package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.CartItemDto;
import co.edu.sena.tu_unidad.entity.CartItemEntity;
import co.edu.sena.tu_unidad.entity.ProductEntity;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.CartItemRepository;
import co.edu.sena.tu_unidad.repository.ProductRepository;
import co.edu.sena.tu_unidad.repository.ProductImageRepository;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<CartItemDto> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CartItemDto addToCart(Long userId, Long productId, Integer quantity) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println(">>> addToCart llamado para userId=" + userId + ", productId=" + productId);

        Optional<CartItemEntity> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
            return convertToDto(item);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
            return convertToDto(newItem);
        }
    }

    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(cartItemRepository::delete);
    }

    public void updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        cartItemRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                });
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    private CartItemDto convertToDto(CartItemEntity entity) {
        String imageUrl = productImageRepository.findByProductId(entity.getProduct().getId()).stream()
                .findFirst()
                .map(img -> img.getImageUrl())
                .orElse("");

        return CartItemDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getName())
                .productPrice(entity.getProduct().getPrice())
                .quantity(entity.getQuantity())
                .imageUrl(imageUrl)
                .build();
    }
}

package co.edu.sena.tu_unidad.controller;
import co.edu.sena.tu_unidad.dto.CartItemDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ServerResponseDto getCartItems(@PathVariable Long userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Cart items retrieved successfully")
                .data(cartItems)
                .build();
    }

    @PostMapping("/{userId}/add")
    public ServerResponseDto addToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        CartItemDto cartItem = cartService.addToCart(userId, productId, quantity);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Product added to cart successfully")
                .data(cartItem)
                .build();
    }

    @DeleteMapping("/{userId}/remove")
    public ServerResponseDto removeFromCart(
            @PathVariable Long userId,
            @RequestParam Long productId) {
        cartService.removeFromCart(userId, productId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Product removed from cart successfully")
                .data(true)
                .build();
    }

    @PutMapping("/{userId}/update")
    public ServerResponseDto updateCartItemQuantity(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        cartService.updateCartItemQuantity(userId, productId, quantity);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Cart item quantity updated successfully")
                .data(true)
                .build();
    }

    @DeleteMapping("/{userId}/clear")
    public ServerResponseDto clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Cart cleared successfully")
                .data(true)
                .build();
    }
}


package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ReviewDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ServerResponseDto getProductReviews(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getProductReviews(productId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Product reviews retrieved successfully")
                .data(reviews)
                .build();
    }

    @PostMapping("/{userId}/product/{productId}")
    public ServerResponseDto addReview(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody ReviewDto reviewDto) {
        ReviewDto review = reviewService.addReview(userId, productId, reviewDto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Review added successfully")
                .data(review)
                .build();
    }

    @PutMapping("/{userId}/product/{productId}")
    public ServerResponseDto updateReview(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody ReviewDto reviewDto) {
        ReviewDto review = reviewService.updateReview(userId, productId, reviewDto);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Review updated successfully")
                .data(review)
                .build();
    }

    @DeleteMapping("/{userId}/product/{productId}")
    public ServerResponseDto deleteReview(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        reviewService.deleteReview(userId, productId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Review deleted successfully")
                .data(true)
                .build();
    }
}

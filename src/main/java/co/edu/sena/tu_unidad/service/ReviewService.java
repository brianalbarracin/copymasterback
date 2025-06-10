package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.ReviewDto;
import co.edu.sena.tu_unidad.entity.ProductEntity;
import co.edu.sena.tu_unidad.entity.ReviewEntity;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.ProductRepository;
import co.edu.sena.tu_unidad.repository.ReviewRepository;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ReviewDto> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto addReview(Long userId, Long productId, ReviewDto reviewDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verificar si el usuario ya ha revisado este producto
        Optional<ReviewEntity> existingReview = reviewRepository.findByUserIdAndProductId(userId, productId);
        if (existingReview.isPresent()) {
            throw new RuntimeException("You have already reviewed this product");
        }

        ReviewEntity review = new ReviewEntity();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCreatedAt(new Date());

        review = reviewRepository.save(review);
        return convertToDto(review);
    }

    public ReviewDto updateReview(Long userId, Long productId, ReviewDto reviewDto) {
        ReviewEntity review = reviewRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review = reviewRepository.save(review);

        return convertToDto(review);
    }

    public void deleteReview(Long userId, Long productId) {
        reviewRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(reviewRepository::delete);
    }

    private ReviewDto convertToDto(ReviewEntity entity) {
        return ReviewDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getName())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

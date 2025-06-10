package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.*;
import co.edu.sena.tu_unidad.entity.*;
import co.edu.sena.tu_unidad.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.sena.tu_unidad.repository.ProductImageRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {


    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public OrderDto createOrder(Long userId, Long addressId, String paymentMethod) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Calcular total
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // Crear orden
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus("PENDING");
        order.setTotal(total);
        order.setCreatedAt(new Date());
        order = orderRepository.save(order);

        // Crear items de la orden
        for (CartItemEntity cartItem : cartItems) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);

            // Actualizar stock
            ProductEntity product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Crear pago
        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        paymentRepository.save(payment);

        // Vaciar carrito
        cartItemRepository.deleteByUserId(userId);

        return convertToDto(order);
    }

    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElse(null);
    }

    public void updateOrderStatus(Long orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }

    public void updatePaymentStatus(Long orderId, String status) {
        paymentRepository.findByOrderId(orderId).ifPresent(payment -> {
            payment.setPaymentStatus(status);
            if ("PAID".equals(status)) {
                payment.setPaidAt(new Date());
            }
            paymentRepository.save(payment);
        });
    }

    private OrderDto convertToDto(OrderEntity entity) {
        List<OrderItemDto> items = orderItemRepository.findByOrderId(entity.getId()).stream()
                .map(this::convertToItemDto)
                .collect(Collectors.toList());

        PaymentDto payment = paymentRepository.findByOrderId(entity.getId())
                .map(this::convertToPaymentDto)
                .orElse(null);

        return OrderDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .addressId(entity.getAddress().getId())
                .status(entity.getStatus())
                .total(entity.getTotal())
                .createdAt(entity.getCreatedAt())
                .items(items)
                .payment(payment)
                .build();
    }

    private OrderItemDto convertToItemDto(OrderItemEntity entity) {
        String imageUrl = productImageRepository.findByProductId(entity.getProduct().getId()).stream()
                .findFirst()
                .map(img -> img.getImageUrl())
                .orElse("");

        return OrderItemDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getName())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .imageUrl(imageUrl)
                .build();
    }

    private PaymentDto convertToPaymentDto(PaymentEntity entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .paidAt(entity.getPaidAt())
                .build();
    }
}

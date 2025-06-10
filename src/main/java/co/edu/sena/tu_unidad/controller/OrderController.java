package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.OrderDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ServerResponseDto createOrder(
            @RequestParam Long userId,
            @RequestParam Long addressId,
            @RequestParam String paymentMethod) {
        OrderDto order = orderService.createOrder(userId, addressId, paymentMethod);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .data(order)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ServerResponseDto getUserOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("User orders retrieved successfully")
                .data(orders)
                .build();
    }

    @GetMapping("/{orderId}")
    public ServerResponseDto getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ServerResponseDto.builder()
                .status(order != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(order != null ? "Order retrieved successfully" : "Order not found")
                .data(order)
                .build();
    }

    @PutMapping("/{orderId}/status")
    public ServerResponseDto updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Order status updated successfully")
                .data(true)
                .build();
    }

    @PutMapping("/{orderId}/payment-status")
    public ServerResponseDto updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        orderService.updatePaymentStatus(orderId, status);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Payment status updated successfully")
                .data(true)
                .build();
    }



    @GetMapping("/check-wompi-status")
    public ServerResponseDto checkWompiStatusAndCreateOrder(
            @RequestParam String transactionId,
            @RequestParam Long userId,
            @RequestParam Long addressId) {

        try {
            // Llama a Wompi (desde backend) y consulta estado de transacción
            String wompiUrl = "https://sandbox.wompi.co/v1/transactions/" + transactionId;

            // Llama a Wompi usando RestTemplate o WebClient
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(wompiUrl, Map.class);

            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            String status = (String) data.get("status");

            if ("APPROVED".equalsIgnoreCase(status)) {
                // Crear orden si aún no existe (opcionalmente puedes revisar eso)
                OrderDto order = orderService.createOrder(userId, addressId, "card");

                return ServerResponseDto.builder()
                        .status(201)
                        .message("Orden creada con éxito.")
                        .data(order)
                        .build();
            } else {
                return ServerResponseDto.builder()
                        .status(400)
                        .message("La transacción no fue aprobada: " + status)
                        .build();
            }

        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(500)
                    .message("Error al verificar la transacción: " + e.getMessage())
                    .build();
        }
    }

}


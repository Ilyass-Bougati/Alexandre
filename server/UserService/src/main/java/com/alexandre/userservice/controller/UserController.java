package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.OrderDTO;
import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final WebClient webClient;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("/order/{userId}/{productId}")
    public String makeOrder(@PathVariable UUID userId, @PathVariable UUID productId) {
        OrderDTO orderDTO = OrderDTO.builder()
                .userId(userId)
                .productId(productId)
                .build();

        webClient.post()
                .uri("http://localhost:8082/api/v1")
                .bodyValue(orderDTO)
                .retrieve()
                .toBodilessEntity()
                .subscribe();

        return "ok";
    }
}

package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.OrderDTO;
import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/api/v1")
public class UserController {
    private final UserService userService;
    private final WebClient.Builder webClient;

    @GetMapping("/")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/{productId}")
    public String createProduct(@PathVariable UUID userId, @PathVariable UUID productId) {
        OrderDTO order = OrderDTO.builder()
                .productId(productId)
                .userId(userId)
                .build();

        webClient.build().post()
                .uri("http://orderservice/api/v1/order")
                .bodyValue(order)
                .retrieve()
                .toBodilessEntity()
                .subscribe();

        return "ok";
    }

}

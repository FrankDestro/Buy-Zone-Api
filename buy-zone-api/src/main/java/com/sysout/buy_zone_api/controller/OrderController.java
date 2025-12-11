package com.sysout.buy_zone_api.controller;

import com.sysout.buy_zone_api.models.dto.OrderDTO;
import com.sysout.buy_zone_api.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService service;

    @Operation(tags = "Orders", summary = "Buscar pedido por id", description = "Retorna os dados do pedido pelo id (requer ROLE_ADMIN ou ROLE_OPERATOR)")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(tags = "Orders", summary = "Registrar novo pedido", description = "Cria um novo pedido (requer ROLE_ADMIN ou ROLE_OPERATOR)")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<OrderDTO> recordOder(@Valid @RequestBody OrderDTO dto) {
        dto = service.recordOrder(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}

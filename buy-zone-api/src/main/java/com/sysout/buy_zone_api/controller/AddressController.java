package com.sysout.buy_zone_api.controller;

import com.sysout.buy_zone_api.models.dto.AddressDTO;
import com.sysout.buy_zone_api.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    private final AddressService addressService;

    @Operation(tags = "Address", summary = "Listar todos os endereços", description = "Retorna uma lista completa de endereços (requer ROLE_ADMIN ou ROLE_OPERATOR)")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAllAddressList() {
        List<AddressDTO> list = addressService.findAllAddressList();
        return ResponseEntity.ok().body(list);
    }

    @Operation(tags = "Address", summary = "Buscar endereço por id", description = "Retorna os dados de um endereço pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        AddressDTO addressDTO = addressService.findById(id);
        return ResponseEntity.ok().body(addressDTO);
    }

    @Operation(tags = "Address", summary = "Inserir novo endereço", description = "Cria um novo endereço com os dados fornecidos")
    @PostMapping
    public ResponseEntity<AddressDTO> insertAddress(@Valid @RequestBody AddressDTO dto) {
        dto = addressService.insertAddress(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(tags = "Address", summary = "Atualizar endereço", description = "Atualiza os dados de um endereço pelo id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        dto = addressService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}

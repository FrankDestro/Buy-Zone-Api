package com.sysout.buy_zone_api.controller;

import com.sysout.buy_zone_api.models.dto.PhoneDTO;
import com.sysout.buy_zone_api.services.PhoneService;
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
@RequestMapping(value = "/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @Operation(tags = "Phones", summary = "Salvar número de telefone", description = "Insere um novo número de telefone (requer ROLE_ADMIN ou ROLE_OPERATOR)")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<PhoneDTO> savePhoneNumber(@Valid @RequestBody PhoneDTO phoneDTO) {
        phoneDTO = phoneService.savePhoneNumber(phoneDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(phoneDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(phoneDTO);
    }

    @Operation(tags = "Phones", summary = "Deletar número de telefone", description = "Remove um número de telefone pelo id (requer ROLE_ADMIN ou ROLE_OPERATOR)")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        phoneService.deletePhoneNumber(id);
        return ResponseEntity.noContent().build();
    }
}

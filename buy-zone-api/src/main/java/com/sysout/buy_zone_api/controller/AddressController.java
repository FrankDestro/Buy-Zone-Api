package com.sysout.buy_zone_api.controller;

import com.sysout.buy_zone_api.models.dto.AddressDTO;
import com.sysout.buy_zone_api.services.AddressService;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAllAddressList() {
        List<AddressDTO> list = addressService.findAllAddressList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        AddressDTO addressDTO = addressService.findById(id);
        return ResponseEntity.ok().body(addressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insertAddress(@Valid @RequestBody AddressDTO dto) {
        dto = addressService.insertAddress(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        dto = addressService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}

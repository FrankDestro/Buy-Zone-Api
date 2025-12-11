package com.sysout.buy_zone_api.controller;

import com.sysout.buy_zone_api.BuyZoneApiApplication;
import com.sysout.buy_zone_api.models.dto.ProductDTO;
import com.sysout.buy_zone_api.models.dto.ProductMinDTO;
import com.sysout.buy_zone_api.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(BuyZoneApiApplication.class);

    @Operation(tags = "Products", summary = "Filtrar produtos com detalhes", description = "Filtra produtos pela categoria e subfiltros com paginação")
    @GetMapping("/filter/details")
    public ResponseEntity<Page<ProductMinDTO>> filterProducts(
            @RequestParam("category") String category,
            @RequestParam Map<String, String> subfilters,
            Pageable pageable) {
        Page<ProductMinDTO> products = productService.findProductsFilters(category, subfilters, pageable);
        return ResponseEntity.ok().body(products);
    }

    @Operation(tags = "Products", summary = "Buscar produto por id", description = "Retorna os dados completos de um produto pelo seu id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        logger.info("TEST DE LOGGING");
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(tags = "Products", summary = "Listar todos os produtos com filtro por nome", description = "Retorna uma página de produtos com filtro opcional pelo nome")
    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            Pageable pageable) {
        Page<ProductMinDTO> dto = productService.findAllProducts(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @Operation(tags = "Products", summary = "Inserir novo produto", description = "Cria um novo produto (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(tags = "Products", summary = "Atualizar produto", description = "Atualiza um produto existente pelo id (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = productService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(tags = "Products", summary = "Deletar produto", description = "Remove um produto pelo id (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

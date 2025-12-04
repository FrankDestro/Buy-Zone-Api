package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.ProductImageDTO;
import com.sysout.buy_zone_api.models.entities.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ProductImagesDTOMapper {

    ProductImageDTO toProductImagesDTO(ProductImage productImage);
    ProductImage toEntity(ProductImageDTO productImageDTO);

}

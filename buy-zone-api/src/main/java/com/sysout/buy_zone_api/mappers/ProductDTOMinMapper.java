package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.ProductMinDTO;
import com.sysout.buy_zone_api.models.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductImagesDTOMapper.class)
public interface ProductDTOMinMapper {

    ProductMinDTO toProductMinDTO(Product product);

}
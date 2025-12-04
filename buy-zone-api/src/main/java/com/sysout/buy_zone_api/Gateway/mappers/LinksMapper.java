package com.sysout.buy_zone_api.Gateway.mappers;

import com.sysout.buy_zone_api.Gateway.models.entities.Link;
import com.sysout.buy_zone_api.Gateway.models.entities.LinkQrcodes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface LinksMapper {

    List<Link> toListLinks(List<Link> arrangements);

    List<LinkQrcodes> toListLinksQrcode(List<LinkQrcodes> qrcodes);


}

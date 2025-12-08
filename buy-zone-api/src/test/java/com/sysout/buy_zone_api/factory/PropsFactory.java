package com.sysout.buy_zone_api.factory;

import com.sysout.buy_zone_api.enums.PropType;
import com.sysout.buy_zone_api.models.entities.Props;

public class PropsFactory {

    public static Props createProps() {
        Props prop = new Props();
        prop.setId(1L);
        prop.setName("value1");
        prop.setType(PropType.PRODUCT);
        prop.setProduct(ProductFactory.createProduct());

        return prop;
    }
}

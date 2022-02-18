package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.entity.Product;
import java.util.List;

public class ProductMapper {
    public static Product map(List<String> fields) {
        String id = fields.get(0);
        String name = fields.get(1);
        String price = fields.get(2);

        Product.ProductBuilder builder = Product.builder();
        if (id != null) builder.id(Integer.parseInt(id));
        if (name != null) builder.name(name);
        if (price != null) builder.price(Double.parseDouble(price));

        return builder.build();
    }
}

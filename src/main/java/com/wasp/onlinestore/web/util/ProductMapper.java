package com.wasp.onlinestore.web.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.wasp.onlinestore.entity.Product;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product getProductFromFields(List<String> fields) {
        String id = fields.get(0);
        String name = fields.get(1);
        String price = fields.get(2);

        Product.ProductBuilder builder = Product.builder();
        if (id != null) builder.id(Integer.parseInt(id));
        if (name != null) builder.name(name);
        if (price != null) builder.price(Double.parseDouble(price));

        return builder.build();
    }

    public static Product getProductFromRequestBody(HttpServletRequest req) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        try (JsonParser parser = JsonFactory.builder().build().createParser(body)) {
            Product.ProductBuilder builder = Product.builder();
            if (parser.nextToken() != JsonToken.START_OBJECT) {
                throw new IOException("Expected data to start with an Object");
            }
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                if (parser.getCurrentToken().equals(JsonToken.VALUE_STRING)) {
                    String value = parser.getText();
                    switch (parser.getCurrentName()) {
                        case "id":
                            builder.id(Integer.parseInt(value));
                            break;
                        case "name":
                            builder.name(value);
                            break;
                        case "price":
                            builder.price(Double.parseDouble(value));
                            break;
                        default:
                            throw new RuntimeException("Unexpected field in json: " + parser.getCurrentName());
                    }
                }
            }
            return builder.build();
        }
    }
}

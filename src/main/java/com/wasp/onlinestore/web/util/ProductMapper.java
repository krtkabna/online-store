package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import lombok.experimental.UtilityClass;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.StringReader;

@UtilityClass
public class ProductMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Product getProductFromRequestBody(String requestBody) {
        StringReader body = new StringReader(requestBody);
        try {
            return MAPPER.readValue(body, Product.class);
        } catch (IOException e) {
            throw new ProductParseException("Could not parse Product json: " + body, e);
        }
    }
}
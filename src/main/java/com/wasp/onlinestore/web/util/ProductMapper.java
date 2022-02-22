package com.wasp.onlinestore.web.util;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class ProductMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Product getProductFromFields(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        try {
            return Product.builder()
                .id(Integer.parseInt(id))
                .name(name)
                .price(Double.parseDouble(price))
                .build();
        } catch (NumberFormatException | NullPointerException e) {
            throw new ProductParseException("Could not parse product from request parameters", e);
        }

    }

    public static Product getProductFromRequestBody(HttpServletRequest req) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            return OBJECT_MAPPER.readValue(body, Product.class);
        } catch (InvalidDefinitionException e) {
            throw new ProductParseException("Request body structure is invalid or it contains invalid values", e);
        }
    }
}

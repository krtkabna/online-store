package com.wasp.onlinestore.web.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
        throw new UnsupportedOperationException();
    }

    public static Product getProductFromFields(HttpServletRequest req) {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        try {
            return Product.builder()
                .name(name)
                .price(Double.parseDouble(price))
                .build();
        } catch (NumberFormatException | NullPointerException e) {
            throw new ProductParseException("Could not parse product from request parameters", e);
        }

    }

    public static Product getProductFromRequestBody(HttpServletRequest req) throws IOException {
        try {
            StringReader body = new StringReader(req.getReader().lines().collect(Collectors.joining()));
            return new Gson().fromJson(body, Product.class);
        } catch (JsonParseException e) {
            throw new ProductParseException("Request body structure is invalid or it contains invalid values", e);
        }
    }
}

package com.wasp.onlinestore.web.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import lombok.experimental.UtilityClass;
import java.io.StringReader;

@UtilityClass
public class ProductMapper {
    public static final Gson GSON = new Gson();

    public Product getProductFromRequestBody(String requestBody) {
        try {
            StringReader body = new StringReader(requestBody);
            //use jackson
            return GSON.fromJson(body, Product.class);
        } catch (JsonParseException e) {
            throw new ProductParseException("Request body structure is invalid or it contains invalid values", e);
        }
    }
}
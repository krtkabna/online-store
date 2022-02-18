package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.entity.Product;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ProductPageVariablesMapper {

    public static List<String> getFields(HttpServletRequest req) {
        List<String> result = new ArrayList<>();
        result.add(req.getParameter("id"));
        result.add(req.getParameter("name"));
        result.add(req.getParameter("price"));
        return result;
    }

    public static Map<String, Object> createFieldsMap(List<String> fields) {
        Map<String, Object> map = new HashMap<>();
        for (String s : fields) {
            map.put(s, "");
        }
        return map;
    }
}

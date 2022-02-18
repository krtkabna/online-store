package com.wasp.onlinestore.entity;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private Date creationDate;
}

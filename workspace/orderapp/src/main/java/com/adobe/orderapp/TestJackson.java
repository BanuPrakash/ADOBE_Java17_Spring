package com.adobe.orderapp;

import com.adobe.orderapp.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
    public static void main(String[] args) throws Exception{
        Product p = new Product(22,"Test", 45000.00, 120, 1);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(p));
    }
}

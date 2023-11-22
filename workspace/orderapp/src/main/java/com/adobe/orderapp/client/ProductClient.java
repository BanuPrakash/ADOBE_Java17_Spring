package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClient implements CommandLineRunner {
    @Autowired
    private OrderService service;
    @Override
    public void run(String... args) throws Exception {
        //addProducts();
        listProducts();
    }

    private void listProducts() {
        List<Product> products = service.getProducts();
        for(Product p : products) {
            System.out.println(p);
        }
    }

    private void addProducts() {
        Product p1 = Product.builder()
                .name("iPhone 14")
                .price(89000.00)
                .quantity(100)
                .build();

        Product p2 = Product.builder()
                .name("Wacom")
                .price(8000.00)
                .quantity(100)
                .build();

        service.addProduct(p1);
        service.addProduct(p2);

    }
}

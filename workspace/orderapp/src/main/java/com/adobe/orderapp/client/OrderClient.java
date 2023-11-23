package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Customer;
import com.adobe.orderapp.entity.Item;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderClient implements CommandLineRunner {
    private final OrderService service;

    @Override
    public void run(String... args) throws Exception {
       // placeOrder();
    }

    private void placeOrder() {
        Order order = new Order();
        Customer customer = Customer.builder().email("raj@adobe.com").build();
        order.setCustomer(customer);

        List<Item> items = new ArrayList<>();
        items.add(Item
                .builder()
                .product(Product.builder().id(3).build())
                        .quantity(1)
                .build());

        items.add(Item
                .builder()
                .product(Product.builder().id(1).build())
                .quantity(2)
                .build());

     order.setItems(items);

     service.placeOrder(order);
    }
}

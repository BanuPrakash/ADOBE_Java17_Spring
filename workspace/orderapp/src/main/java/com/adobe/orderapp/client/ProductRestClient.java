package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ProductRestClient implements CommandLineRunner {
    private final RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
      //  getUsers();
       // getProduct();
        //addProduct();
        getAllProducts();
    }

    private void getAllProducts() {
        ResponseEntity<List<Product>> response =
                restTemplate.exchange("http://localhost:8080/api/products",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                });

        List<Product> products = response.getBody();
        for(Product p : products) {
            System.out.println(p.getName());
        }
    }

    private void addProduct() {
        Product p =  Product.builder().name("Test").price(89212.11).quantity(100).build();
        ResponseEntity<Product> response =
                restTemplate.postForEntity("http://localhost:8080/api/products", p, Product.class);
        //

    }

    private void getProduct() {
        ResponseEntity<Product> response =
                restTemplate.getForEntity("http://localhost:8080/api/products/1", Product.class);
        System.out.println(response.getStatusCode());
        Product p = response.getBody();
        System.out.println(p);
    }

    private void getUsers() {
        String result = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
        System.out.println(result);
    }
}

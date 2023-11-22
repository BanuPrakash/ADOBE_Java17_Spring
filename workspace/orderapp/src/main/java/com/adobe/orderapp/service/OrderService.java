package com.adobe.orderapp.service;

import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;

    public List<Product> getProducts() {
        return productDao.findAll();
    }

    public Product addProduct(Product p) {
        return productDao.save(p);
    }
}

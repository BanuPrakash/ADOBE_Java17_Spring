package com.adobe.orderapp.service;

import com.adobe.orderapp.dao.OrderDao;
import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.dto.ReportDTO;
import com.adobe.orderapp.entity.Item;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;
    private  final OrderDao orderDao;

    public List<Order> getOrders() {
        return  orderDao.findAll();
    }

    public  List<ReportDTO> getReport() {
        return orderDao.getReport();
    }
    /*
        {
            "customer": {"email":"rita@adobe.com"},
            "items": [
                {"product": {"id": 3}, "quantity": 1},
                {"product": {"id": 1}, "quantity": 2}
            ]
        }
     */
    @Transactional
    public String placeOrder(Order order) {
        List<Item> items = order.getItems(); // incomplete
        double total = 0.0;
        for(Item item : items) {
            Optional<Product> optProduct = productDao.findById(item.getProduct().getId());
            Product p = optProduct.get();
            p.setQuantity(p.getQuantity() - item.getQuantity()); // DIRTY CHECKING --> Update Product
            if(p.getQuantity() < 0) {
                throw new IllegalArgumentException("Product " + p.getName() + " not in Stock!!!");
            }
            item.setAmount(p.getPrice() * item.getQuantity());
            total += item.getAmount();
        }
        order.setTotal(total);
        orderDao.save(order); // items and orders are saved ==> Cascade
        return "order placed!!!";
    }
    public List<Product> getProducts() {
        return productDao.findAll();
    }

    public Product addProduct(Product p) {
        return productDao.save(p);
    }

    @Transactional
    public Product updateProduct(int id, double price) {
        productDao.updateProduct(id, price);
        return productDao.findById(id).get();
    }

    public List<Product> getByRange(double low, double high) {
        return productDao.getByRange(low, high);
    }

    public Product getProductById(int id) {
        return productDao.findById(id).get();
    }

}

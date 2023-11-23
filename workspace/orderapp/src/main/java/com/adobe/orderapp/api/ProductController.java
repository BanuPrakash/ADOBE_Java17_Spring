package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/products")
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService service;

    // http://localhost:8080/api/products
    // GET http://localhost:8080/api/products?low=1000&high=50000
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
                                     @RequestParam(name="high", defaultValue = "0.0") double high) {
         if(low == 0.0 && high == 0.0) {
            return service.getProducts();
        } else {
            return service.getByRange(low, high);
        }
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") int id)  {
        return service.getProductById(id);
    }

    // POST {name:"A", "price":11} ==> content-type:application/json
    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product p) {
        p = service.addProduct(p);
        return new ResponseEntity<Product>(p, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p)  {
        return service.updateProduct(id, p.getPrice());
    }

    // Avoid
    @DeleteMapping("/{id}")
    public @ResponseBody List<Product> delete(@PathVariable("id") int id) {
        return service.getProducts();
    }
}

package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.ReportDTO;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public List<Order> getOrders() {
        return orderService.getOrders();
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
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
        return "order placed!!!";
    }

    @GetMapping("/report")
    public List<ReportDTO> getReport() {
        return orderService.getReport();
    }
}

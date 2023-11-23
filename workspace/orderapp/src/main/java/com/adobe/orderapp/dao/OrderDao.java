package com.adobe.orderapp.dao;

import com.adobe.orderapp.dto.ReportDTO;
import com.adobe.orderapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
//    @Query("select c.email, c.firstName, o.orderDate, o.total from Order o inner join o.customer c")
//    List<Object[]> getReport();

    @Query("select new com.adobe.orderapp.dto.ReportDTO(c.email, c.firstName, o.orderDate, o.total) from Order o inner join o.customer c")
    List<ReportDTO> getReport();
}

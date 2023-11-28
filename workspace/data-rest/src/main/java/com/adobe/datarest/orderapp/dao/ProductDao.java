package com.adobe.datarest.orderapp.dao;

import com.adobe.datarest.orderapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "products" ,collectionResourceRel = "items")
public interface ProductDao extends JpaRepository<Product, Integer> {
    @Query("from Product where price >= :l and price <= :h")
   // @Query(value = "select * from products where price >= :l and price <= :h", nativeQuery = true)
    List<Product> getByRange(@Param("l") double low, @Param("h") double high);

    List<Product> findByQuantity(int id);

    @Modifying
    @Query("update Product set price = :price where id = :id")
    void updateProduct(@Param("id") int id, @Param("price") double price);
}

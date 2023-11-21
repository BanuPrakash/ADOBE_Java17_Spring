package com.adobe.springdemo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoSqlImpl implements  EmployeeDao{

    @Override
    public void addEmployee() {
        System.out.println("Stored in RDBMS...");
    }
}

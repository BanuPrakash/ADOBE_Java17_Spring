package com.adobe.orderapp.dto;

import java.util.Date;

public record ReportDTO(String email, String firstName, Date orderDate, double total) {}

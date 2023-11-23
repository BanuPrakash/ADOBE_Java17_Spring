package com.adobe.orderapp.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; //javax.persisentce.Entity
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private int id;

	@NotBlank(message = "Name is required")
	private String name;

	@Min(value = 10, message = "Price ${validatedValue} should be more than {value}")
	private double price;


	@Min(value = 0, message = "Quantity ${validatedValue} should be more than {value}")
	@Column(name="qty")
	private int quantity;
	
	@Version
	private int ver;
}

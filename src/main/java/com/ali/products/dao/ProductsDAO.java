package com.ali.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ali.products.entitys.Product;

public interface ProductsDAO extends JpaRepository<Product, Long>{
	
}

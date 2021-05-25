package com.products.Products.list.repository;

import com.products.Products.list.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductListId(Long id);
}

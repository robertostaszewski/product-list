package com.products.Products.list.repository;

import com.products.Products.list.entity.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListRepository extends JpaRepository<ProductList, Long> {
}

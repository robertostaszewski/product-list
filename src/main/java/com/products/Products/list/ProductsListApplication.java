package com.products.Products.list;

import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;
import com.products.Products.list.service.ListService;
import com.products.Products.list.service.ProductListService;
import com.products.Products.list.service.Publisher;
import com.products.Products.list.service.SimplePublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsListApplication {

	@Bean
	public ListService listService(ProductListRepository productListRepository, ProductRepository productRepository) {
		return new ProductListService(productListRepository, productRepository);
	}

	@Bean
	public Publisher publisher() {
		return new SimplePublisher();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductsListApplication.class, args);
	}

}

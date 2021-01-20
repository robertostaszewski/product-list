package com.product.productlist.spring;

import com.product.productlist.entity.ProductListId;
import com.product.productlist.presenters.ListPresenter;
import com.product.productlist.presenters.ProductListPresenter;
import com.product.productlist.repository.InMemoryProductListRepository;
import com.product.productlist.repository.InMemoryProductRepository;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.ProductRepository;
import com.product.productlist.services.EventBus;
import com.product.productlist.services.ListService;
import com.product.productlist.services.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ListPresenter listPresenter;

	@Autowired
	private ListService listService;

	@Bean
	public ListService listService(SimpMessagingTemplate template) {
		return new ProductListService(getListRepository(), getProductRepository(), getEventBus(template));
	}

	@Bean
	public ProductRepository getProductRepository() {
		return new InMemoryProductRepository();
	}

	@Bean
	public ProductListRepository getListRepository() {
		return new InMemoryProductListRepository();
	}

	@Bean
	public EventBus getEventBus(SimpMessagingTemplate template) {
		return new WebsocketsEventBus(template);
	}

	@Bean
	public ListPresenter listViewer() {
		return new ProductListPresenter(getListRepository(), getProductRepository());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.listService.createList("Lista zakupowa");
		this.listService.createList("Lista na zakupy");
		this.listPresenter.getAllLists().subList(0, 1).forEach(listDetails -> {
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
		});

		this.listPresenter.getAllLists().subList(1, 2).forEach(listDetails -> {
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "woda");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "ziemniaki");
		});
	}
}

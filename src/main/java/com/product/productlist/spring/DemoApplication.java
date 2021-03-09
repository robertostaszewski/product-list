package com.product.productlist.spring;

import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.User;
import com.product.productlist.entity.Username;
import com.product.productlist.presenters.ListPresenter;
import com.product.productlist.presenters.ProductListPresenter;
import com.product.productlist.repository.*;
import com.product.productlist.services.*;
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

	@Autowired
	private UserRepository userRepository;

	@Bean
	public ListService listService(SimpMessagingTemplate template) {
		return new ProductListService(getListRepository(), getProductRepository(), getEventBus(template), productListFactory(), productFactory());
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
	public UserRepository getUserRepository() {
		return new InMemoryUserRepository();
	}

	@Bean
	public SharingEntryRepository sharingEntryRepository() {
		return new InMemorySharingEntryRepository();
	}

	@Bean
	public SharingService sharingService() {
		return new ProductListSharingService(getListRepository(), new SimpleSharingEntryFactory(), sharingEntryRepository());
	}

	@Bean
	public EventBus getEventBus(SimpMessagingTemplate template) {
		return new WebsocketsEventBus(template);
	}

	@Bean
	public ListPresenter listViewer() {
		return new ProductListPresenter(getListRepository(), getProductRepository());
	}

	@Bean
	public ProductFactory productFactory() {
		return new SimpleProductFactory();
	}

	@Bean
	public ProductListFactory productListFactory() {
		return new SimpleProductListFactory();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Username username1 = Username.from("1");
		Username username2 = Username.from("2");
		userRepository.save(new User(username1));
		userRepository.save(new User(username2));

		this.listService.createList("Lista zakupowa", username1);
		this.listService.createList("Lista zakupowa", username2);
		this.listService.createList("Lista na zakupy", username1);
		this.listPresenter.getAllListsForUser(username1).subList(0, 1).forEach(listDetails -> {
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
		});

		this.listPresenter.getAllListsForUser(username1).subList(1, 2).forEach(listDetails -> {
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "woda");
			this.listService.addProduct(ProductListId.from(listDetails.getId()), "ziemniaki");
		});

		listPresenter.getAllListsForUser(username2).forEach(list -> {
			listService.addProduct(ProductListId.from(list.getId()), "woda");
		});
	}
}

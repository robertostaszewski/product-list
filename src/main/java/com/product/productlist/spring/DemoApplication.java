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
	private ListsService listsService;

	@Autowired
	private UserRepository userRepository;

	@Bean
	public ListsService listService(ProductListService productListService,
									ProductService productService,
									SharingService sharingService,
									EventBus eventBus) {
		return new NotifyOnSuccessListsService(productListService, productService, sharingService, eventBus);
	}

	@Bean
	public ProductListService productListService(ProductListRepository productListRepository) {
		return new RepoProductListService(productListRepository, new SimpleProductListFactory());
	}

	@Bean
	public ProductService productService(ProductRepository productRepository) {
		return new RepoProductService(productRepository, new SimpleProductFactory());
	}

	@Bean
	public SharingService sharingService(SharingEntryRepository sharingEntryRepository) {
		return new RepoSharingService(sharingEntryRepository, new SimpleSharingEntryFactory());
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
	public EventBus getEventBus(SimpMessagingTemplate template) {
		return new WebsocketsEventBus(template);
	}

	@Bean
	public ListPresenter listPresenter(ProductListService productListService,
									   ProductService productService,
									   SharingService sharingService) {
		return new ProductListPresenter(productListService, productService, sharingService);
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

		this.listsService.createList("Lista zakupowa", username1);
		this.listsService.createList("Lista zakupowa", username2);
		this.listsService.createList("Lista na zakupy", username1);
		this.listPresenter.getAllListsFor(username1).subList(0, 1).forEach(listDetails -> {
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
		});

		this.listPresenter.getAllListsFor(username1).subList(1, 2).forEach(listDetails -> {
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "mleko");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "jajka");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "mąka");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "woda");
			this.listsService.addProduct(ProductListId.from(listDetails.getId()), "ziemniaki");
		});

		listPresenter.getAllListsFor(username2).forEach(list -> {
			listsService.addProduct(ProductListId.from(list.getId()), "woda");
		});
	}
}

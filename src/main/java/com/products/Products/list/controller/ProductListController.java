package com.products.Products.list.controller;

import com.products.Products.list.entity.Product;
import com.products.Products.list.entity.ProductList;
import com.products.Products.list.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@CrossOrigin
public class ProductListController {
    private final ListService listService;
    private final Publisher publisher;

    public ProductListController(ListService listService, Publisher publisher) {
        this.listService = listService;
        this.publisher = publisher;
    }

    @GetMapping("/lists")
    public List<ProductList> getAllLists() {
        return listService.execute(new GetAllLists());
    }

    @PostMapping("/lists/add")
    public ProductList createList(@RequestBody CreateListRequest createListRequest) {
        return listService.execute(new CreateList(createListRequest.getListName()));
    }

    @DeleteMapping("/lists/{listId}")
    public ProductList removeList(@PathVariable("listId") Long listId) {
        return listService.execute(new RemoveList(listId).andThen(productList -> publisher.ping()));
    }

    @GetMapping("/lists/{listId}/products")
    public List<Product> getAllForListId(@PathVariable("listId") Long listId) {
        return listService.execute(new GetProductsForList(listId));
    }

    @GetMapping("/lists/flux")
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter();
        Subscriber subscriber = new SseSubscriber(sseEmitter);
        subscriber.subscribe(publisher);
        return sseEmitter;
    }

    @PostMapping("/lists/{listId}/products/add")
    public Product addProduct(@PathVariable("listId") Long listId, @RequestBody AddProductRequest addProductRequest) {
        return listService.execute(new AddProduct(listId, addProductRequest.getProductDescription()).andThen(product -> publisher.ping()));
    }

    @DeleteMapping("/lists/{listId}/products/{productId}")
    public Product remove(@PathVariable("listId") Long listId, @PathVariable("productId") Long productId) {
        return listService.execute(new RemoveProduct(productId).andThen(product -> publisher.ping()));
    }
}

package com.products.Products.list.controller;

import com.products.Products.list.entity.Product;
import com.products.Products.list.entity.ProductList;
import com.products.Products.list.service.*;
import com.products.Products.list.commands.*;
import com.products.Products.list.pubsub.Publisher;
import com.products.Products.list.pubsub.SseSubscriber;
import com.products.Products.list.pubsub.Subscriber;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

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
        return new GetAllLists().execute(listService);
    }

    @PostMapping("/lists/add")
    public ProductList createList(@RequestBody CreateListRequest createListRequest) {
        return Commands.createList(createListRequest.getListName())
                .onComplete(publisher::ping)
                .execute(listService);
    }

    @DeleteMapping("/lists/{listId}")
    public ProductList removeList(@PathVariable("listId") Long listId) {
        return Commands.removeList(listId)
                .onComplete(publisher::ping)
                .execute(listService);
    }

    @GetMapping("/lists/{listId}/products")
    public List<Product> getAllForListId(@PathVariable("listId") Long listId) {
        return Commands.getList(listId)
                .andThenMap(ProductList::getId)
                .andThen(Commands::getProductsForList)
                .execute(listService);
    }

    @PostMapping("/lists/{listId}/products/add")
    public Product addProduct(@PathVariable("listId") Long listId, @RequestBody AddProductRequest addProductRequest) {
        return Commands.getList(listId)
                .andThen(productList -> Commands.addProduct(productList, addProductRequest.getProductDescription()))
                .onComplete(publisher::ping)
                .execute(listService);
    }

    @DeleteMapping("/lists/{listId}/products/{productId}")
    public Product removeProduct(@PathVariable("listId") Long listId, @PathVariable("productId") Long productId) {
        return Commands.removeProduct(listId, productId)
                .onComplete(publisher::ping)
                .execute(listService);
    }

    @GetMapping("/lists/flux")
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter();
        new SseSubscriber(sseEmitter).subscribe(publisher);
        return sseEmitter;
    }
}

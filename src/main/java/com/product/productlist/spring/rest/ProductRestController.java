package com.product.productlist.spring.rest;

import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.presenters.ListPresenter;
import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.services.ListService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin
public class ProductRestController {
    private final ListPresenter listPresenter;
    private final ListService listService;

    public ProductRestController(ListPresenter listPresenter, ListService listService) {
        this.listPresenter = listPresenter;
        this.listService = listService;
    }

    @GetMapping("/rest/lists/{listId}")
    public ProductListDetails getListDetails(@PathVariable("listId") String listId) {
        return listPresenter.getListDetails(ProductListId.from(listId));
    }

    @GetMapping("/rest/lists")
    public List<ProductListDetails> getAllListDetails() {
        return listPresenter.getAllLists();
    }

    @GetMapping("/rest/lists/{listId}/products")
    public List<ProductDetails> getProducts(@PathVariable(name = "listId") String listId) {
        return listPresenter.getProductsForList(ProductListId.from(listId));
    }

    @PutMapping("/rest/lists/{listId}")
    public void addProduct(@PathVariable("listId") String listId, @PathParam("productName") String productName) {
        listService.addProduct(ProductListId.from(listId), productName);
    }

    @DeleteMapping("/rest/lists/{listId}/{productId}")
    public void deleteProduct(@PathVariable("listId") String listId, @PathVariable("productId") String productId) {
        listService.removeProduct(ProductId.from(productId));
    }

    @PutMapping("/rest/lists")
    public void addList(@PathParam("listName") String listName) {
        listService.createList(listName);
    }

    @DeleteMapping("/rest/lists/{listId}")
    public void deleteList(@PathVariable("listId") String listId) {
        listService.removeList(ProductListId.from(listId));
    }
}

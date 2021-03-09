package com.product.productlist.spring.rest;

import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;
import com.product.productlist.presenters.ListPresenter;
import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.services.ListService;
import com.product.productlist.services.SharingService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ProductRestController {
    private final ListPresenter listPresenter;
    private final ListService listService;
    private final SharingService sharingService;

    public ProductRestController(ListPresenter listPresenter, ListService listService, SharingService sharingService) {
        this.listPresenter = listPresenter;
        this.listService = listService;
        this.sharingService = sharingService;
    }

    @GetMapping("/rest/lists/{listId}")
    public ProductListDetails getListDetails(@PathVariable("listId") String listId) {
        return listPresenter.getListDetails(ProductListId.from(listId));
    }

    @GetMapping("/rest/lists")
    public List<ProductListDetails> getAllListDetails(Principal principal) {
        return listPresenter.getAllListsForUser(Username.from(principal.getName()));
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
    public void addList(@PathParam("listName") String listName, Principal principal) {
        listService.createList(listName, Username.from(principal.getName()));
    }

    @DeleteMapping("/rest/lists/{listId}")
    public void deleteList(@PathVariable("listId") String listId) {
        listService.removeList(ProductListId.from(listId));
    }

    @PostMapping("/rest/lists/{listId}")
    public void shareList(@PathVariable("listId") String listId, @PathParam("userId") String userId) {
        sharingService.shareListWith(ProductListId.from(listId), Username.from(userId));
    }
}

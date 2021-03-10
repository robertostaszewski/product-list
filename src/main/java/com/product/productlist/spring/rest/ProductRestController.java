package com.product.productlist.spring.rest;

import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;
import com.product.productlist.presenters.ListPresenter;
import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.services.ListsService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ProductRestController {
    private final ListPresenter listPresenter;
    private final ListsService listsService;

    public ProductRestController(ListPresenter listPresenter, ListsService listsService) {
        this.listPresenter = listPresenter;
        this.listsService = listsService;
    }

    @GetMapping("/rest/lists/{listId}")
    public ProductListDetails getListDetails(@PathVariable("listId") String listId) {
        return listPresenter.getListDetails(ProductListId.from(listId));
    }

    @GetMapping("/rest/lists")
    public List<ProductListDetails> getAllListDetails(Principal principal) {
        return listPresenter.getAllListsFor(Username.from(principal.getName()));
    }

    @GetMapping("/rest/lists/shared")
    public List<ProductListDetails> getAllSharedListDetails(Principal principal) {
        return listPresenter.getAllListsSharedWith(Username.from(principal.getName()));
    }

    @GetMapping("/rest/lists/{listId}/products")
    public List<ProductDetails> getProducts(@PathVariable(name = "listId") String listId) {
        return listPresenter.getProductsForList(ProductListId.from(listId));
    }

    @PutMapping("/rest/lists/{listId}")
    public void addProduct(@PathVariable("listId") String listId, @PathParam("productName") String productName) {
        listsService.addProduct(ProductListId.from(listId), productName);
    }

    @DeleteMapping("/rest/lists/{listId}/{productId}")
    public void deleteProduct(@PathVariable("listId") String listId, @PathVariable("productId") String productId) {
        listsService.removeProduct(ProductId.from(productId));
    }

    @PutMapping("/rest/lists")
    public void addList(@PathParam("listName") String listName, Principal principal) {
        listsService.createList(listName, Username.from(principal.getName()));
    }

    @DeleteMapping("/rest/lists/{listId}")
    public void deleteList(@PathVariable("listId") String listId) {
        listsService.removeList(ProductListId.from(listId));
    }

    @PostMapping("/rest/lists/{listId}")
    public void shareList(@PathVariable("listId") String listId, @PathParam("userId") String userId) {
        listsService.shareListWith(ProductListId.from(listId), Username.from(userId));
    }
}

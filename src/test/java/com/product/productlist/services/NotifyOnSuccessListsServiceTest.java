package com.product.productlist.services;

import com.product.productlist.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotifyOnSuccessListsServiceTest {

    @Mock
    private ProductListService productListService;

    @Mock
    private ProductService productService;

    @Mock
    private SharingService sharingService;

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private NotifyOnSuccessListsService listsService;

    @Test
    void givenListNameAndUsername_createList_shouldCreateNewListAndSendEvent() {
        String listName = "shopping list";
        Username username = Username.from("user");
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListService).newList(listName, username);

        listsService.createList(listName, username);

        verify(eventBus).listCreated(productList);
        verifyNoMoreInteractions(productListService, eventBus);
    }

    @Test
    void givenNewProductNameAndListId_addProduct_shouldCallProductServiceNewListAndSendEvent() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListService).getList(productListId);

        String productName = "bread";
        Product product = mock(Product.class);
        doReturn(product).when(productService).newProduct(productName, productList);

        listsService.addProduct(productListId, productName);

        verify(eventBus).productAdded(product);
        verifyNoMoreInteractions(productListService, productService, eventBus);
    }

    @Test
    void givenProductAlreadyInTheList_addProduct_shouldThrowRuntimeException() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListService).getList(productListId);

        String productName = "bread";
        doThrow(new RuntimeException("Product already in the list " + productName)).when(productService).newProduct(productName, productList);

        assertThrows(RuntimeException.class, () -> listsService.addProduct(productListId, productName),"Product already in the list " + productName);

        verifyNoMoreInteractions(productService);
        verifyNoInteractions(eventBus);
    }

    @Test
    void givenListIdThatDoesNotExists_addProduct_shouldThrowRuntimeException() {
        ProductListId productListId = ProductListId.random();
        doThrow(new RuntimeException("Shopping list " + productListId.asString() + " does not exists")).when(productListService).getList(productListId);

        assertThrows(RuntimeException.class, () -> listsService.addProduct(productListId, "bread"),"Shopping list " + productListId.asString() + " does not exists");

        verifyNoMoreInteractions(productListService);
        verifyNoInteractions(productService, eventBus);
    }

    @Test
    void givenProduct_removeProduct_shouldRemoveAndSendEvent() {
        ProductId productId = ProductId.random();
        Product product = mock(Product.class);
        doReturn(product).when(productService).get(productId);

        listsService.removeProduct(productId);

        verify(productService).remove(product);
        verify(eventBus).productRemoved(product);
        verifyNoMoreInteractions(productService, eventBus);
    }

    @Test
    void givenProductThatDoesNotExists_removeProduct_shouldThrowRuntimeException() {
        ProductId productId = ProductId.random();
        doThrow(new RuntimeException("Product not exists " + productId.asString())).when(productService).get(productId);

        assertThrows(RuntimeException.class, () -> listsService.removeProduct(productId), "Product not exists " + productId.asString());

        verifyNoMoreInteractions(productService);
        verifyNoInteractions(eventBus);
    }

    @Test
    void givenProductListId_removeList_shouldRemoveConnectedProductsSharingGroupsAndTheList() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListService).getList(productListId);

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        doReturn(List.of(product1, product2)).when(productService).getProducts(productListId);

        SharingEntry sharingEntry1 = mock(SharingEntry.class);
        SharingEntry sharingEntry2 = mock(SharingEntry.class);
        doReturn(List.of(sharingEntry1, sharingEntry2)).when(sharingService).getEntriesWith(productListId);

        listsService.removeList(productListId);

        verify(productService).remove(product1);
        verify(productService).remove(product2);
        verify(sharingService).remove(sharingEntry1);
        verify(sharingService).remove(sharingEntry2);
        verify(productListService).remove(productList);
        verify(eventBus).listRemoved(productList);
        verifyNoMoreInteractions(productService, productListService, sharingService, eventBus);
    }

    @Test
    void givenProductListIdForNotSharedAndEmptyList_removeList_shouldRemoveOnlyTheList() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListService).getList(productListId);

        doReturn(List.of()).when(productService).getProducts(productListId);

        doReturn(List.of()).when(sharingService).getEntriesWith(productListId);

        listsService.removeList(productListId);

        verify(productListService).remove(productList);
        verify(eventBus).listRemoved(productList);
        verifyNoMoreInteractions(productListService, eventBus, productService, sharingService);
    }

    @Test
    void givenNotExistingProductListId_removeList_shouldRethrowRuntimeException() {
        ProductListId productListId = ProductListId.random();
        doThrow(new RuntimeException("Shopping list " + productListId.asString() + " does not exists")).when(productListService).getList(productListId);

        assertThrows(RuntimeException.class, () -> listsService.removeList(productListId), "List not exists " + productListId.asString());

        verifyNoMoreInteractions(productListService);
        verifyNoInteractions(productService, sharingService, eventBus);
    }

}
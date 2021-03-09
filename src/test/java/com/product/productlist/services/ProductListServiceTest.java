package com.product.productlist.services;

import com.product.productlist.entity.*;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductListServiceTest {

    @Mock
    private ProductListRepository productListRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EventBus eventBus;

    @Mock
    private ProductFactory productFactory;

    @Mock
    private ProductListFactory productListFactory;

    @InjectMocks
    private ProductListService productListService;

    @Test
    void createList() {
        String listName = "shopping list";
        Username username = Username.from("user");
        ProductList expectedList = mock(ProductList.class);
        doReturn(expectedList).when(productListFactory).newList(listName, username);

        productListService.createList(listName, username);

        verify(productListRepository).save(expectedList);
        verify(eventBus).listCreated(expectedList);
    }

    @Test
    void addProduct() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(Optional.of(productList)).when(productListRepository).get(productListId);
        String productName = "bread";
        Product product = mock(Product.class);
        doReturn(product).when(productFactory).newProduct(productList, productName);
        doReturn(false).when(productRepository).productExists(product);

        productListService.addProduct(productListId, productName);

        verify(productRepository).save(product);
        verify(eventBus).productAdded(product);
    }

    @Test
    void addProduct2() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(Optional.of(productList)).when(productListRepository).get(productListId);
        String productName = "bread";
        Product product = mock(Product.class);
        doReturn(product).when(productFactory).newProduct(productList, productName);
        doReturn(true).when(productRepository).productExists(product);

        Assertions.assertThrows(RuntimeException.class, () -> productListService.addProduct(productListId, productName),"Product already in the list " + productName);

        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(eventBus);
    }

    @Test
    void addProduct1() {
        ProductListId productListId = ProductListId.random();
        doReturn(Optional.empty()).when(productListRepository).get(productListId);
        String productName = "bread";

        Assertions.assertThrows(RuntimeException.class, () -> productListService.addProduct(productListId, productName),"Shopping list " + productListId.asString() + " does not exists");

        verifyNoInteractions(productRepository, eventBus);
    }

    @Test
    void removeProduct() {
        ProductId productId = ProductId.random();
        Product product = mock(Product.class);
        doReturn(Optional.of(product)).when(productRepository).get(productId);

        productListService.removeProduct(productId);

        verify(productRepository).remove(product);
        verify(eventBus).productRemoved(product);
    }

    @Test
    void removeProduct1() {
        ProductId productId = ProductId.random();
        doReturn(Optional.empty()).when(productRepository).get(productId);

        Assertions.assertThrows(RuntimeException.class, () -> productListService.removeProduct(productId), "Product not exists " + productId.asString());

        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(eventBus);
    }

    @Test
    void removeList() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(Optional.of(productList)).when(productListRepository).get(productListId);
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        doReturn(List.of(product1, product2)).when(productRepository).getAllForList(productListId);

        productListService.removeList(productListId);

        verify(productRepository).remove(product1);
        verify(productRepository).remove(product2);
        verify(productListRepository).remove(productList);
        verify(eventBus).listRemoved(productList);
        verifyNoMoreInteractions(productRepository, productListRepository, eventBus);
        verifyNoInteractions(productFactory, productListFactory);
    }

    @Test
    void removeList1() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(Optional.of(productList)).when(productListRepository).get(productListId);
        doReturn(List.of()).when(productRepository).getAllForList(productListId);

        productListService.removeList(productListId);

        verify(productListRepository).remove(productList);
        verify(eventBus).listRemoved(productList);
        verifyNoMoreInteractions(productListRepository, eventBus, productRepository);
        verifyNoInteractions(productFactory, productListFactory);
    }

    @Test
    void removeList2() {
        ProductListId productListId = ProductListId.random();
        doReturn(Optional.empty()).when(productListRepository).get(productListId);

        Assertions.assertThrows(RuntimeException.class, () -> productListService.removeList(productListId), "List not exists " + productListId.asString());

        verifyNoMoreInteractions(productListRepository);
        verifyNoInteractions(productFactory, productListFactory, productRepository, eventBus);
    }

}
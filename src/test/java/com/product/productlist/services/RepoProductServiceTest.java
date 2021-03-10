package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepoProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private RepoProductService productService;

    @Test
    public void newProduct() {
        String productName = "product";
        ProductList productList = mock(ProductList.class);
        Product product = mock(Product.class);
        doReturn(product).when(productFactory).newProduct(productName, productList);

        doReturn(false).when(productRepository).productExists(product);

        productService.newProduct(productName, productList);

        verify(productRepository).save(product);
        verifyNoMoreInteractions(productRepository, productFactory);
    }

    @Test
    public void newProduct1() {
        String productName = "product";
        ProductList productList = mock(ProductList.class);
        Product product = mock(Product.class);
        doReturn(product).when(productFactory).newProduct(productName, productList);

        doReturn(true).when(productRepository).productExists(product);

        assertThrows(RuntimeException.class, () -> productService.newProduct(productName, productList));

        verify(productRepository, never()).save(product);
        verifyNoMoreInteractions(productRepository, productFactory);
    }

    @Test
    public void get() {
        ProductId productId = ProductId.random();
        Product product = mock(Product.class);
        doReturn(Optional.of(product)).when(productRepository).get(productId);

        Product returnedProduct = productService.get(productId);

        assertEquals(product, returnedProduct);
    }

    @Test
    public void get1() {
        ProductId productId = ProductId.random();
        doReturn(Optional.empty()).when(productRepository).get(productId);

        assertThrows(RuntimeException.class, () -> productService.get(productId));
    }

    @Test
    public void getProducts() {
        ProductListId productListId = ProductListId.random();
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        List<Product> products = List.of(product1, product2);
        doReturn(products).when(productRepository).getAllForList(productListId);

        List<Product> returnedProducts = productService.getProducts(productListId);

        assertEquals(products, returnedProducts);
    }

    @Test
    public void getProducts1() {
        ProductListId productListId = ProductListId.random();
        doReturn(List.of()).when(productRepository).getAllForList(productListId);

        List<Product> returnedProducts = productService.getProducts(productListId);

        assertTrue(returnedProducts.isEmpty());
    }

    @Test
    public void remove() {
        Product product = mock(Product.class);

        productService.remove(product);

        verify(productRepository).remove(product);
        verifyNoMoreInteractions(productRepository);
    }
}
package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.ProductListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepoProductListServiceTest {

    @Mock
    private ProductListRepository productListRepository;

    @Mock
    private ProductListFactory productListFactory;

    @InjectMocks
    private RepoProductListService productListService;

    @Test
    public void givenListNameAndUsername_newList_shouldCreateNewListAndSaveToRepo() {
        String name = "list";
        Username username = Username.from("user");
        ProductList productList = mock(ProductList.class);
        doReturn(productList).when(productListFactory).newList(name, username);

        ProductList returnedList = productListService.newList(name, username);

        Assertions.assertEquals(productList, returnedList);
        verify(productListRepository).save(productList);
    }

    @Test
    public void givenProductListId_getList_shouldReturnListFromRepo() {
        ProductListId productListId = ProductListId.random();
        ProductList productList = mock(ProductList.class);
        doReturn(Optional.of(productList)).when(productListRepository).get(productListId);

        ProductList returnedList = productListService.getList(productListId);

        Assertions.assertEquals(productList, returnedList);
    }

    @Test
    public void givenProductListIdThatDoesNotExists_getList_shouldThrowRuntimeException() {
        ProductListId productListId = ProductListId.random();
        doReturn(Optional.empty()).when(productListRepository).get(productListId);

        assertThrows(RuntimeException.class, () -> productListService.getList(productListId));
    }

    @Test
    public void givenUser_getAllListsForUser_shouldReturnListsOwnedByTheUser() {
        Username username = Username.from("user");
        ProductList productList1 = mock(ProductList.class);
        ProductList productList2 = mock(ProductList.class);
        List<ProductList> productLists = List.of(productList1, productList2);
        doReturn(productLists).when(productListRepository).getAllForUser(username);

        List<ProductList> allListsForUser = productListService.getAllListsForUser(username);

        assertEquals(productLists, allListsForUser);
    }

    @Test
    public void givenUserWithoutLists_getAllListsForUser_shouldReturnEmptyList() {
        Username username = Username.from("user");
        List<ProductList> productLists = List.of();
        doReturn(productLists).when(productListRepository).getAllForUser(username);

        List<ProductList> allListsForUser = productListService.getAllListsForUser(username);

        assertEquals(productLists, allListsForUser);
    }

    @Test
    public void GivenProductList_remove_shouldRemoveFromRepo() {
        ProductList productList = mock(ProductList.class);

        productListService.remove(productList);

        verify(productListRepository).remove(productList);
    }
}
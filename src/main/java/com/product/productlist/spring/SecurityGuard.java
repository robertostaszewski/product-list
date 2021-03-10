package com.product.productlist.spring;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.SharingEntryRepository;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class SecurityGuard {
    private final ProductListRepository productListRepository;
    private final SharingEntryRepository sharingEntryRepository;

    public SecurityGuard(ProductListRepository productListRepository, SharingEntryRepository sharingEntryRepository) {
        this.productListRepository = productListRepository;
        this.sharingEntryRepository = sharingEntryRepository;
    }

    public boolean isOwnerOfTheList(Authentication authentication, String listId) {
        Username username = Username.from(authentication.getName());
        Optional<ProductList> optionalProductList = productListRepository.get(ProductListId.from(listId));

        if (optionalProductList.isPresent()) {
            ProductList productList = optionalProductList.get();
            return productList.getOwner().equals(username);
        } else {
            return false;
        }
    }

    public boolean isListSharedWith(Authentication authentication, String listId) {
        Username username = Username.from(authentication.getName());

        return sharingEntryRepository.getAllForUser(username).stream()
                .anyMatch(sharingEntry -> sharingEntry.getProductListId().equals(ProductListId.from(listId)));
    }
}

package com.product.productlist.spring;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductList;
import com.product.productlist.services.EventBus;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebsocketsEventBus implements EventBus {

    private final SimpMessagingTemplate template;

    public WebsocketsEventBus(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void listCreated(ProductList productList) {
        template.convertAndSend("/topic/me", productList.getProductListId().asString());
    }

    @Override
    public void listRemoved(ProductList productList) {
        template.convertAndSend("/topic/me", productList.getProductListId().asString());
    }

    @Override
    public void productAdded(Product product) {
        template.convertAndSend("/topic/" + product.getProductListId().asString(), "product_added");
    }

    @Override
    public void productRemoved(Product product) {
        template.convertAndSend("/topic/" + product.getProductListId().asString(), "product_removed");
    }
}

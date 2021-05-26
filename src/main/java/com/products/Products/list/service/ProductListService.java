package com.products.Products.list.service;

import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;
import com.products.Products.list.commands.Command;

public class ProductListService implements ListService {
    private final RepositoryCommandsActions repositoryCommandsActions;

    public ProductListService(ProductListRepository productListRepository, ProductRepository productRepository) {
        repositoryCommandsActions = new RepositoryCommandsActions(productListRepository, productRepository);
    }

    @Override
    public <T> T execute(Command<T> command) {
        return command.execute(repositoryCommandsActions);
    }
}

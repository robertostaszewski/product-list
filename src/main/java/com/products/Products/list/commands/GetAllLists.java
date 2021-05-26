package com.products.Products.list.commands;

import com.products.Products.list.entity.ProductList;

import java.util.List;

public class GetAllLists implements Command<List<ProductList>>{

    @Override
    public List<ProductList> execute(CommandActions commandActions) {
        return commandActions.getAllLists();
    }
}

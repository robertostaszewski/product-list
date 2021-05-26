package com.products.Products.list.commands;

public interface CommandExecutor {

    <T> T execute(Command<T> command);
}

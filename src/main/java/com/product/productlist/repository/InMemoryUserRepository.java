package com.product.productlist.repository;

import com.product.productlist.entity.User;
import com.product.productlist.entity.Username;

import java.util.Optional;

public class InMemoryUserRepository extends InMemoryRepository<User, Username> implements UserRepository {

    public InMemoryUserRepository() {
        super(User::getUsername);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return getAll().stream().filter(user -> user.getUsername().equals(Username.from(username))).findFirst();
    }
}

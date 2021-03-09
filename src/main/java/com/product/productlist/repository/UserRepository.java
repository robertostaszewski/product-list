package com.product.productlist.repository;

import com.product.productlist.entity.User;
import com.product.productlist.entity.Username;

import java.util.Optional;

public interface UserRepository  extends Repository<User, Username> {

    Optional<User> getByUsername(String username);
}

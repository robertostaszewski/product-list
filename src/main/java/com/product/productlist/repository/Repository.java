package com.product.productlist.repository;

import com.product.productlist.entity.Id;

import java.util.List;
import java.util.Optional;

public interface Repository<T, I extends Id> {

    void save(T t);

    void update(T t);

    Optional<T> get(I id);

    List<T> getAll();

    void remove(T t);
}

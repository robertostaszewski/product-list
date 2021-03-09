package com.product.productlist.repository;

import com.product.productlist.entity.Id;

import java.util.*;
import java.util.function.Function;

public class InMemoryRepository<C, I extends Id> implements Repository<C, I> {
    private final Map<I, C> itemMap = new HashMap<>();
    private final ToId<C, I> toId;

    public InMemoryRepository(ToId<C, I> toId) {
        this.toId = toId;
    }

    @Override
    public void save(C c) {
        itemMap.put(toId.apply(c), c);
    }

    @Override
    public void update(C c) {
        if (itemMap.containsKey(toId.apply(c))) {
            itemMap.replace(toId.apply(c), c);
        }
    }

    @Override
    public Optional<C> get(I id) {
        return itemMap.containsKey(id) ? Optional.of(itemMap.get(id)) : Optional.empty();
    }

    @Override
    public List<C> getAll() {
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public void remove(C c) {
        itemMap.remove(toId.apply(c));
    }

    public interface ToId<T, I> extends Function<T, I> {
    }
}

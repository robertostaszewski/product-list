package com.product.productlist.repository;

import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.SharingEntryId;
import com.product.productlist.entity.Username;

import java.util.List;
import java.util.stream.Collectors;

public class InMemorySharingEntryRepository extends InMemoryRepository<SharingEntry, SharingEntryId> implements SharingEntryRepository {
    public InMemorySharingEntryRepository() {
        super(SharingEntry::getSharingEntryId);
    }

    @Override
    public boolean isAlreadyShared(SharingEntry sharingEntry) {
        return getAll().stream().anyMatch(se -> se.getProductListId().equals(sharingEntry.getProductListId()) && se.getUsername().equals(sharingEntry.getUsername()));
    }

    @Override
    public List<SharingEntry> getEntriesFor(Username username) {
        return getAll().stream()
                .filter(sharingEntry -> sharingEntry.getUsername().equals(username))
                .collect(Collectors.toList());
    }
}

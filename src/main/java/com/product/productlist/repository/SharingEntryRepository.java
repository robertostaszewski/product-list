package com.product.productlist.repository;

import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.SharingEntryId;
import com.product.productlist.entity.Username;

import java.util.List;

public interface SharingEntryRepository extends Repository<SharingEntry, SharingEntryId> {

    boolean isAlreadyShared(SharingEntry sharingEntry);

    List<SharingEntry> getEntriesFor(Username username);
}

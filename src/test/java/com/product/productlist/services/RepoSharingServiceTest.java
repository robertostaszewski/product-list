package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.SharingEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepoSharingServiceTest {

    @Mock
    private SharingEntryRepository sharingEntryRepository;

    @Mock
    private SharingEntryFactory sharingEntryFactory;

    @InjectMocks
    private RepoSharingService repoSharingService;

    @Test
    public void createNewEntry() {
        Username username = Username.from("user");
        ProductList productList = mock(ProductList.class);
        SharingEntry sharingEntry = mock(SharingEntry.class);
        doReturn(sharingEntry).when(sharingEntryFactory).newEntry(productList, username);

        doReturn(false).when(sharingEntryRepository).isAlreadyShared(sharingEntry);

        SharingEntry newEntry = repoSharingService.newEntry(productList, username);

        assertEquals(sharingEntry, newEntry);
        verify(sharingEntryRepository).save(sharingEntry);
        verifyNoMoreInteractions(sharingEntryRepository, sharingEntryFactory);
    }

    @Test
    public void createNewEntry1() {
        Username username = Username.from("user");
        ProductList productList = mock(ProductList.class);
        SharingEntry sharingEntry = mock(SharingEntry.class);
        doReturn(sharingEntry).when(sharingEntryFactory).newEntry(productList, username);

        doReturn(true).when(sharingEntryRepository).isAlreadyShared(sharingEntry);

        assertThrows(RuntimeException.class, () -> repoSharingService.newEntry(productList, username));

        verify(sharingEntryRepository, never()).save(sharingEntry);
        verifyNoMoreInteractions(sharingEntryRepository, sharingEntryFactory);
    }

    @Test
    public void getSharedWith() {
        Username username = Username.from("user");
        SharingEntry sharingEntry1 = mock(SharingEntry.class);
        SharingEntry sharingEntry2 = mock(SharingEntry.class);
        List<SharingEntry> entries = List.of(sharingEntry1, sharingEntry2);
        doReturn(entries).when(sharingEntryRepository).getAllForUser(username);

        List<SharingEntry> resultEntries = repoSharingService.getSharedWith(username);

        assertEquals(entries, resultEntries);
    }

    @Test
    public void getSharedWith1() {
        Username username = Username.from("user");
        List<SharingEntry> entries = List.of();
        doReturn(entries).when(sharingEntryRepository).getAllForUser(username);

        List<SharingEntry> resultEntries = repoSharingService.getSharedWith(username);

        assertEquals(entries, resultEntries);
    }

    @Test
    public void getEntriesWith() {
        ProductListId productListId = ProductListId.random();
        SharingEntry sharingEntry1 = mock(SharingEntry.class);
        SharingEntry sharingEntry2 = mock(SharingEntry.class);
        List<SharingEntry> entries = List.of(sharingEntry1, sharingEntry2);
        doReturn(entries).when(sharingEntryRepository).getAllForList(productListId);

        List<SharingEntry> resultEntries = repoSharingService.getEntriesWith(productListId);

        assertEquals(entries, resultEntries);
    }

    @Test
    public void getEntriesWith1() {
        ProductListId productListId = ProductListId.random();
        List<SharingEntry> entries = List.of();
        doReturn(entries).when(sharingEntryRepository).getAllForList(productListId);

        List<SharingEntry> resultEntries = repoSharingService.getEntriesWith(productListId);

        assertEquals(entries, resultEntries);
    }

    @Test
    public void remove() {
        SharingEntry sharingEntry = mock(SharingEntry.class);

        repoSharingService.remove(sharingEntry);

        verify(sharingEntryRepository).remove(sharingEntry);
    }
}
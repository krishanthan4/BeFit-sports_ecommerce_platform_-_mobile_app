package com.sprinsec.mobile_v2;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.util.repository.WatchlistRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WatchlistRepositoryTest {

    private WatchlistRepository watchlistRepository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        watchlistRepository = new WatchlistRepository(context);
        watchlistRepository.open();
    }

    @After
    public void tearDown() {
        watchlistRepository.close();
    }

    @Test
    public void testUpdateWatchlist() {
        List<UserHomeProductModel> products = new ArrayList<>();
        products.add(new UserHomeProductModel("1", "Product 1", "10.0", "imagePath1", "4.5"));
        products.add(new UserHomeProductModel("2", "Product 2", "20.0", "imagePath2", "4.0"));

        watchlistRepository.updateWatchlist(products);

        List<UserHomeProductModel> watchlist = watchlistRepository.getWatchlist();
        assertEquals(2, watchlist.size());
        assertEquals("Product 1", watchlist.get(0).getProductName());
        assertEquals("Product 2", watchlist.get(1).getProductName());
    }

    @Test
    public void testGetWatchlist() {
        List<UserHomeProductModel> watchlist = watchlistRepository.getWatchlist();
        assertNotNull(watchlist);
    }

    @Test
    public void testDeleteWatchlistItem() {
        List<UserHomeProductModel> products = new ArrayList<>();
        products.add(new UserHomeProductModel("1", "Product 1", "10.0", "imagePath1", "4.5"));
        watchlistRepository.updateWatchlist(products);

        watchlistRepository.deleteWatchlistItem("1");
        List<UserHomeProductModel> watchlist = watchlistRepository.getWatchlist();
        assertTrue(watchlist.isEmpty());
    }
}
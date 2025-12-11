package com.sprinsec.mobile_v2;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.sprinsec.mobile_v2.data.model.UserCartModel;
import com.sprinsec.mobile_v2.util.repository.CartRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CartRepositoryTest {

    private CartRepository cartRepository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        cartRepository = new CartRepository(context);
        cartRepository.open();
    }

    @After
    public void tearDown() {
        cartRepository.close();
    }

    @Test
    public void testUpdateCart() {
        List<UserCartModel> products = new ArrayList<>();
        products.add(new UserCartModel("1", "Product 1", "Description 1", "10.0", "1", "1", "imagePath1", "4.5", "5.0", "2.0"));
        products.add(new UserCartModel("2", "Product 2", "Description 2", "20.0", "2", "2", "imagePath2", "4.0", "5.0", "2.0"));

        cartRepository.updateCart(products);

        List<UserCartModel> cart = cartRepository.getCart();
        assertEquals(2, cart.size());
        assertEquals("Product 1", cart.get(0).getCartProductName());
        assertEquals("Product 2", cart.get(1).getCartProductName());
    }

    @Test
    public void testGetCart() {
        List<UserCartModel> cart = cartRepository.getCart();
        assertNotNull(cart);
    }

    @Test
    public void testDeleteCartItem() {
        List<UserCartModel> products = new ArrayList<>();
        products.add(new UserCartModel("1", "Product 1", "Description 1", "10.0", "1", "1", "imagePath1", "4.5", "5.0", "2.0"));
        cartRepository.updateCart(products);

        cartRepository.deleteCartItem("1");
        List<UserCartModel> cart = cartRepository.getCart();
        assertTrue(cart.isEmpty());
    }
}
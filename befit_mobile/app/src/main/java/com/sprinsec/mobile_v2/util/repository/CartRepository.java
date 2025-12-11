package com.sprinsec.mobile_v2.util.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sprinsec.mobile_v2.data.db.SQLiteDatabaseHelper;
import com.sprinsec.mobile_v2.data.model.UserCartModel;
import com.sprinsec.mobile_v2.util.contract.CartContract;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public CartRepository(Context context) {
        dbHelper = SQLiteDatabaseHelper.getInstance(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void updateCart(List<UserCartModel> products) {
        open();
        database.beginTransaction();
        try {
            // Clear existing data
            database.delete(CartContract.CartEntry.TABLE_NAME, null, null);

            // Insert new data
            for (UserCartModel product : products) {
                ContentValues values = new ContentValues();
                values.put(CartContract.CartEntry.COLUMN_PRODUCT_ID, product.getCartProductId());
                values.put(CartContract.CartEntry.COLUMN_TITLE, product.getCartProductName());
                values.put(CartContract.CartEntry.COLUMN_PRICE, product.getCartProductPrice());
                values.put(CartContract.CartEntry.COLUMN_DESCRIPTION, product.getCartProductDescription());
                values.put(CartContract.CartEntry.COLUMN_PRODUCT_QTY, product.getCartProductQuantity());
                values.put(CartContract.CartEntry.COLUMN_CART_QTY, product.getCartQuantity());
                values.put(CartContract.CartEntry.COLUMN_AVG_STARS, product.getCartProductRatings());
                values.put(CartContract.CartEntry.COLUMN_DELIVERY_FEE, product.getCartProductDeliveryFee());
                values.put(CartContract.CartEntry.COLUMN_IMAGE_PATH, product.getCartProductImage());
                values.put(CartContract.CartEntry.COLUMN_SHIPPING_FEE, product.getCartProductShippingFee());

                database.insert(CartContract.CartEntry.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            close();
        }
    }

    public ArrayList<UserCartModel> getCart() {
        ArrayList<UserCartModel> cart = new ArrayList<>();
        open();

        Cursor cursor = database.query(
                CartContract.CartEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_PRODUCT_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_TITLE));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_DESCRIPTION));
                String productQty = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_PRODUCT_QTY));
                String cartQty = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_CART_QTY));
                String avgStars = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_AVG_STARS));
                String deliveryFee = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_DELIVERY_FEE));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_IMAGE_PATH));
                String shippingFee = cursor.getString(cursor.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_SHIPPING_FEE));

                cart.add(new UserCartModel(id, title, description, price, productQty, cartQty, imagePath, avgStars, deliveryFee, shippingFee));
            }
        } finally {
            cursor.close();
            close();
        }

        return cart;
    }

    public void deleteCartItem(String productId) {
        open();
        database.delete(
                CartContract.CartEntry.TABLE_NAME,
                CartContract.CartEntry.COLUMN_PRODUCT_ID + " = ?",
                new String[]{productId}
        );
        close();
    }
}
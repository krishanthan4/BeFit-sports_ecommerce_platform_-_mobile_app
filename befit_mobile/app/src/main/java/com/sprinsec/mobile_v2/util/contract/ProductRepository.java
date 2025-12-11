package com.sprinsec.mobile_v2.util.contract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sprinsec.mobile_v2.data.db.SQLiteDatabaseHelper;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public ProductRepository(Context context) {
        dbHelper = SQLiteDatabaseHelper.getInstance(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void updateProducts(List<UserHomeProductModel> products) {
        open();
        database.beginTransaction();
        try {
            // Clear existing data
            database.delete(ProductContract.ProductEntry.TABLE_NAME, null, null);

            // Insert new data
            for (UserHomeProductModel product : products) {
                ContentValues values = new ContentValues();
                values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_ID, product.getProductId());
                values.put(ProductContract.ProductEntry.COLUMN_TITLE, product.getProductName());
                values.put(ProductContract.ProductEntry.COLUMN_PRICE, product.getProductPrice());
                values.put(ProductContract.ProductEntry.COLUMN_IMAGE_PATH, product.getProductImage());
                values.put(ProductContract.ProductEntry.COLUMN_AVG_STARS, product.getRating());

                database.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            close();
        }
    }

    public ArrayList<UserHomeProductModel> getProducts() {
        ArrayList<UserHomeProductModel> product = new ArrayList<>();
        open();

        Cursor cursor = database.query(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRODUCT_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_TITLE));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRICE));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_IMAGE_PATH));
                String avgStars = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_AVG_STARS));

                product.add(new UserHomeProductModel(id, title, price, imagePath, avgStars));
            }
        } finally {
            cursor.close();
            close();
        }

        return product;
    }

}

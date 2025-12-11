package com.sprinsec.mobile_v2.util.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sprinsec.mobile_v2.data.db.SQLiteDatabaseHelper;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.util.contract.WatchlistContract;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public WatchlistRepository(Context context) {
        dbHelper = SQLiteDatabaseHelper.getInstance(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void updateWatchlist(List<UserHomeProductModel> products) {
        open();
        database.beginTransaction();
        try {
            // Clear existing data
            database.delete(WatchlistContract.WatchlistEntry.TABLE_NAME, null, null);

            // Insert new data
            for (UserHomeProductModel product : products) {
                ContentValues values = new ContentValues();
                values.put(WatchlistContract.WatchlistEntry.COLUMN_PRODUCT_ID, product.getProductId());
                values.put(WatchlistContract.WatchlistEntry.COLUMN_TITLE, product.getProductName());
                values.put(WatchlistContract.WatchlistEntry.COLUMN_PRICE, product.getProductPrice());
                values.put(WatchlistContract.WatchlistEntry.COLUMN_IMAGE_PATH, product.getProductImage());
                values.put(WatchlistContract.WatchlistEntry.COLUMN_AVG_STARS, product.getRating());

                database.insert(WatchlistContract.WatchlistEntry.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            close();
        }
    }

    public ArrayList<UserHomeProductModel> getWatchlist() {
        ArrayList<UserHomeProductModel> watchlist = new ArrayList<>();
        open();

        Cursor cursor = database.query(
                WatchlistContract.WatchlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(WatchlistContract.WatchlistEntry.COLUMN_PRODUCT_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(WatchlistContract.WatchlistEntry.COLUMN_TITLE));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(WatchlistContract.WatchlistEntry.COLUMN_PRICE));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(WatchlistContract.WatchlistEntry.COLUMN_IMAGE_PATH));
                String avgStars = cursor.getString(cursor.getColumnIndexOrThrow(WatchlistContract.WatchlistEntry.COLUMN_AVG_STARS));

                watchlist.add(new UserHomeProductModel(id, title, price, imagePath, avgStars));
            }
        } finally {
            cursor.close();
            close();
        }

        return watchlist;
    }

    public void deleteWatchlistItem(String productId) {
        open();
        database.delete(
                WatchlistContract.WatchlistEntry.TABLE_NAME,
                WatchlistContract.WatchlistEntry.COLUMN_PRODUCT_ID + " = ?",
                new String[]{productId}
        );
        close();
    }
}
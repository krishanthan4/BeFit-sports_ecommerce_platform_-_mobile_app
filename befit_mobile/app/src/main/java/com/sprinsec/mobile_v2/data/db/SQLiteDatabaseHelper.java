package com.sprinsec.mobile_v2.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.util.contract.WatchlistContract;

import java.util.ArrayList;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "befit.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabaseHelper instance;

    public SQLiteDatabaseHelper(@Nullable Context context, @Nullable String name,
                                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static synchronized SQLiteDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteDatabaseHelper(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WATCHLIST_TABLE = "CREATE TABLE watchlist ("
                + "watchlist_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "product_id INTEGER NOT NULL, "  // Changed to INTEGER to match products table
                + "product_name TEXT NOT NULL, "
                + "product_price TEXT NOT NULL, "
                + "product_img TEXT, "
                + "avgStars TEXT NOT NULL);";

        String SQL_CREATE_CART_TABLE = "CREATE TABLE cart ("
                + "cart_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "product_id INTEGER NOT NULL, "  // Changed to INTEGER to match products table
                + "product_name TEXT NOT NULL, "
                + "product_price TEXT NOT NULL, "
                + "product_description TEXT NOT NULL, "
                + "product_qty TEXT NOT NULL, "
                + "cart_qty TEXT NOT NULL, "  // Removed duplicate product_qty
                + "avg_stars TEXT NOT NULL, "
                + "delivery_fee TEXT NOT NULL, "
                + "product_img TEXT, "
                + "shipping_fee TEXT NOT NULL);";

        String SQL_CREATE_HOME_PRODUCT_TABLE = "CREATE TABLE products ("
                + "product_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "product_name TEXT NOT NULL, "
                + "product_price TEXT NOT NULL, "
                + "product_img TEXT, "
                + "avgStars TEXT NOT NULL);";

        try {
            db.execSQL(SQL_CREATE_WATCHLIST_TABLE);
            db.execSQL(SQL_CREATE_CART_TABLE);
            db.execSQL(SQL_CREATE_HOME_PRODUCT_TABLE);

        } catch (SQLException e) {
            Log.e("Database", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS watchlist");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS products");

        // Recreate tables
        onCreate(db);
    }
}

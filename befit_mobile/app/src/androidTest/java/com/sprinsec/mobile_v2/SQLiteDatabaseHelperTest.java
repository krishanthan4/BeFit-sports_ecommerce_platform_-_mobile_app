package com.sprinsec.mobile_v2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sprinsec.mobile_v2.data.db.SQLiteDatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SQLiteDatabaseHelperTest {

    private Context context;
    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        dbHelper = SQLiteDatabaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void testDatabaseCreation() {
        assertNotNull(db);
        assertTrue(db.isOpen());
    }

    @Test
    public void testTablesExist() {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='watchlist'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();

        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='cart'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();

        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='products'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    @Test
    public void testDatabaseUpgrade() {
        dbHelper.onUpgrade(db, 1, 2);

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='watchlist'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();

        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='cart'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();

        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='products'", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }
}
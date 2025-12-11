package com.sprinsec.mobile_v2.util.contract;

import android.provider.BaseColumns;

public class ProductContract {

    public ProductContract() {
    }

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_TITLE = "product_name";
        public static final String COLUMN_PRICE = "product_price";
        public static final String COLUMN_IMAGE_PATH = "product_img";
        public static final String COLUMN_AVG_STARS = "avgStars";
    }
}

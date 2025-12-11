package com.sprinsec.mobile_v2.util.contract;

import android.provider.BaseColumns;

public class CartContract {
    private CartContract() {
    }

    public static class CartEntry implements BaseColumns {
        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_TITLE = "product_name";
        public static final String COLUMN_PRICE = "product_price";
        public static final String COLUMN_DESCRIPTION = "product_description";
        public static final String COLUMN_PRODUCT_QTY = "product_qty";
        public static final String COLUMN_CART_QTY = "cart_qty";
        public static final String COLUMN_AVG_STARS = "avg_stars";
        public static final String COLUMN_DELIVERY_FEE = "delivery_fee";
        public static final String COLUMN_IMAGE_PATH = "product_img";
        public static final String COLUMN_SHIPPING_FEE = "shipping_fee";
    }
}

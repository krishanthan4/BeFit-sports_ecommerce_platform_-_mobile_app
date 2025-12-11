package com.sprinsec.mobile_v2.data.model;

public class UserCartModel {
    private String cartProductId;
    private String cartProductName;
    private String cartProductDescription;
    private String cartProductPrice;
    private String cartProductQuantity;
    private String cartQuantity;
    private String cartProductImage;
    private String cartProductRatings;
    private String cartProductDeliveryFee;
    private String cartProductShippingFee;

    public UserCartModel(String cartProductId, String cartProductName, String cartProductDescription, String cartProductPrice, String cartProductQuantity, String cartQuantity, String cartProductImage, String cartProductRatings, String cartProductDeliveryFee, String cartProductShippingFee) {
        this.cartProductId = cartProductId;
        this.cartProductName = cartProductName;
        this.cartProductDescription = cartProductDescription;
        this.cartProductPrice = cartProductPrice;
        this.cartProductQuantity = cartProductQuantity;
        this.cartQuantity = cartQuantity;
        this.cartProductImage = cartProductImage;
        this.cartProductRatings = cartProductRatings;
        this.cartProductDeliveryFee = cartProductDeliveryFee;
        this.cartProductShippingFee = cartProductShippingFee;
    }

    public String getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(String cartProductId) {
        this.cartProductId = cartProductId;
    }

    public String getCartProductName() {
        return cartProductName;
    }

    public void setCartProductName(String cartProductName) {
        this.cartProductName = cartProductName;
    }

    public String getCartProductDescription() {
        return cartProductDescription;
    }

    public void setCartProductDescription(String cartProductDescription) {
        this.cartProductDescription = cartProductDescription;
    }

    public String getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(String cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public String getCartProductQuantity() {
        return cartProductQuantity;
    }

    public void setCartProductQuantity(String cartProductQuantity) {
        this.cartProductQuantity = cartProductQuantity;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartProductImage() {
        return cartProductImage;
    }

    public void setCartProductImage(String cartProductImage) {
        this.cartProductImage = cartProductImage;
    }

    public String getCartProductRatings() {
        return cartProductRatings;
    }

    public void setCartProductRatings(String cartProductRatings) {
        this.cartProductRatings = cartProductRatings;
    }

    public String getCartProductDeliveryFee() {
        return cartProductDeliveryFee;
    }

    public void setCartProductDeliveryFee(String cartProductDeliveryFee) {
        this.cartProductDeliveryFee = cartProductDeliveryFee;
    }

    public String getCartProductShippingFee() {
        return cartProductShippingFee;
    }

    public void setCartProductShippingFee(String cartProductShippingFee) {
        this.cartProductShippingFee = cartProductShippingFee;
    }
}

package com.sprinsec.mobile_v2.util;

public class Validations {
    public static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailPattern);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() > 0;
    }


}

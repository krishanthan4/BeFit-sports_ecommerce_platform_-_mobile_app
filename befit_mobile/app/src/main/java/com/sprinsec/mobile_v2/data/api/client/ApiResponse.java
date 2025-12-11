package com.sprinsec.mobile_v2.data.api.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public JsonElement data;

    @SerializedName("errors")
    public JsonObject errors;

    public JsonObject getDataAsObject() {
        return data != null && data.isJsonObject() ? data.getAsJsonObject() : new JsonObject();
    }

    public boolean isSuccess() {
        return success;
    }
}


package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("token")
    private String mToken;
    @SerializedName("publishable_key")
    private String mPublishableKey;
    @SerializedName("customer_id")
    private String mCustomerID;
    @SerializedName("pending")
    private Long mPending;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getPublishableKey() {
        return mPublishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        mPublishableKey = publishableKey;
    }

    public String getCustomerID() {
        return mCustomerID;
    }

    public void setCustomerID(String customerID) {
        mCustomerID = customerID;
    }

    public Long getPending() {
        return mPending;
    }

    public void setPending(Long pending) {
        mPending = pending;
    }
}

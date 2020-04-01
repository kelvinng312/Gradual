
package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StripeKeyResponse {

    @SerializedName("publishableKey")
    private String mPublishableKey;

    public String getPublishableKey() {
        return mPublishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        mPublishableKey = publishableKey;
    }

}

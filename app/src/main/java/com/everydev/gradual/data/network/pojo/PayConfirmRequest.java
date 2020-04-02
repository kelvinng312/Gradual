
package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PayConfirmRequest {

    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("payment_intent_id")
    private String mPaymentIntentId;

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getPaymentIntentId() {
        return mPaymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        mPaymentIntentId = paymentIntentId;
    }

}

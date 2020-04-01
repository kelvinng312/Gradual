
package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PayConfirmRequest {

    @SerializedName("payment_intent_id")
    private String mPaymentIntentId;

    public String getPaymentIntentId() {
        return mPaymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        mPaymentIntentId = paymentIntentId;
    }

}

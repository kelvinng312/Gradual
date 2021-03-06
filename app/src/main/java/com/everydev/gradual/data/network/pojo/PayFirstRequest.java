
package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PayFirstRequest {

    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("payment_method_id")
    private String mPaymentMethodId;
    @SerializedName("receive_user_id")
    private Long mReceiveUserId;

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public Long getReceiveUserId() {
        return mReceiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        mReceiveUserId = receiveUserId;
    }

    public String getPaymentMethodId() {
        return mPaymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        mPaymentMethodId = paymentMethodId;
    }
}

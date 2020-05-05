
package com.everydev.gradual.data.network.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PayAgainRequest {

    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("customer_id")
    private String mCustomerId;
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

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }

}


package com.everydev.gradual.data.network.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PayResponse {

    @SerializedName("status")
    private String mStatus;
    @SerializedName("error")
    private String mError;
    @SerializedName("requires_action")
    private boolean mRequiresAction;
    @SerializedName("payment_intent_id")
    private String mPaymentIntentId;
    @SerializedName("client_secret")
    private String mClientSecret;
    @SerializedName("customer_id")
    private String mCustomerId;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public boolean isRequiresAction() {
        return mRequiresAction;
    }

    public void setRequiresAction(boolean requiresAction) {
        mRequiresAction = requiresAction;
    }

    public String getPaymentIntentId() {
        return mPaymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        mPaymentIntentId = paymentIntentId;
    }

    public String getClientSecret() {
        return mClientSecret;
    }

    public void setClientSecret(String clientSecret) {
        mClientSecret = clientSecret;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }
}

package com.everydev.gradual.ui.main;

import android.content.Intent;

import com.everydev.gradual.ui.base.MvpPresenter;
import com.stripe.android.model.PaymentMethodCreateParams;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onViewPrepared();

    void pay(PaymentMethodCreateParams params);

    void onPaymentResult(int requestCode, Intent data);

    String getCustomerId();
}

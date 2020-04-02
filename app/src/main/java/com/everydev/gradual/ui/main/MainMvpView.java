package com.everydev.gradual.ui.main;

import com.everydev.gradual.data.network.pojo.FeedItem;
import com.everydev.gradual.ui.base.MvpView;
import com.stripe.android.Stripe;

import java.util.List;

public interface MainMvpView extends MvpView {
    void showCardInputWidget(boolean show);

    void onPaymentSuccess();

    void handleNextActionForPayment(Stripe stripe, String paymentIntentClientSecret);
}

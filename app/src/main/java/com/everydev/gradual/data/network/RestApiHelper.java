package com.everydev.gradual.data.network;

import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.LoginResponse;
import com.everydev.gradual.data.network.pojo.PayAgainRequest;
import com.everydev.gradual.data.network.pojo.PayConfirmRequest;
import com.everydev.gradual.data.network.pojo.PayFirstRequest;
import com.everydev.gradual.data.network.pojo.PayResponse;
import com.everydev.gradual.data.network.pojo.SignupRequest;
import com.everydev.gradual.data.network.pojo.SignupResponse;
import com.everydev.gradual.data.network.pojo.StripeKeyResponse;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<LoginResponse> login(LoginRequest loginRequest);

    Single<SignupResponse> signup(SignupRequest signupRequest);

    Single<StripeKeyResponse> stripeKey();

    Single<PayResponse> payFirst(PayFirstRequest payFirstRequest);

    Single<PayResponse> payAgain(PayAgainRequest payAgainRequest);

    Single<PayResponse> payConfirm(PayConfirmRequest payConfirmRequest);
}

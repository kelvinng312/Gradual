package com.everydev.gradual.data.network;

import com.everydev.gradual.data.network.pojo.Donee;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.LoginResponse;
import com.everydev.gradual.data.network.pojo.PayAgainRequest;
import com.everydev.gradual.data.network.pojo.PayConfirmRequest;
import com.everydev.gradual.data.network.pojo.PayFirstRequest;
import com.everydev.gradual.data.network.pojo.PayResponse;
import com.everydev.gradual.data.network.pojo.SignupRequest;
import com.everydev.gradual.data.network.pojo.SignupResponse;
import com.everydev.gradual.data.network.pojo.StripeKeyResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RestApiManager implements RestApiHelper {

    NetworkService mService;

    @Inject
    public RestApiManager(NetworkService apiService) {
        mService = apiService;
    }

    @Override
    public Single<LoginResponse> login(LoginRequest loginRequest) {
        return mService.login(loginRequest);
    }

    @Override
    public Single<SignupResponse> signup(SignupRequest signupRequest) {
        return mService.signup(signupRequest);
    }

    @Override
    public Single<StripeKeyResponse> stripeKey() {
        return mService.stripeKey();
    }

    @Override
    public Single<PayResponse> payFirst(PayFirstRequest payFirstRequest) {
        return mService.payFirst(payFirstRequest);
    }

    @Override
    public Single<PayResponse> payAgain(PayAgainRequest payAgainRequest) {
        return mService.payAgain(payAgainRequest);
    }

    @Override
    public Single<PayResponse> payConfirm(PayConfirmRequest payConfirmRequest) {
        return mService.payConfirm(payConfirmRequest);
    }

    @Override
    public Single<List<Donee>> getDonees() {
        return mService.getDonees();
    }
}

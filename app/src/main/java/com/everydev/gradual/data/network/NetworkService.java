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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public interface NetworkService {

    @POST("auth/login")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Single<SignupResponse> signup(@Body SignupRequest signupRequest);

    @GET("donation/stripe-key")
    Single<StripeKeyResponse> stripeKey();

    @POST("donation/pay-first")
    Single<PayResponse> payFirst(PayFirstRequest payFirstRequest);

    @POST("donation/pay-again")
    Single<PayResponse> payAgain(PayAgainRequest payAgainRequest);

    @POST("donation/pay-confirm")
    Single<PayResponse> payConfirm(PayConfirmRequest payConfirmRequest);

}

package com.everydev.gradual.data.network;

import com.everydev.gradual.data.network.pojo.FeedItem;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.LoginResponse;
import com.everydev.gradual.data.network.pojo.PayAgainRequest;
import com.everydev.gradual.data.network.pojo.PayConfirmRequest;
import com.everydev.gradual.data.network.pojo.PayFirstRequest;
import com.everydev.gradual.data.network.pojo.Response;
import com.everydev.gradual.data.network.pojo.SignupRequest;
import com.everydev.gradual.data.network.pojo.SignupResponse;
import com.everydev.gradual.data.network.pojo.StripeKeyResponse;
import com.everydev.gradual.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<LoginResponse> login(LoginRequest loginRequest);

    Single<SignupResponse> signup(SignupRequest signupRequest);

    Single<StripeKeyResponse> stripeKey();

    Single<WrapperResponse<Response>> payFirst(PayFirstRequest payFirstRequest);

    Single<WrapperResponse<Response>> payAgain(PayAgainRequest payAgainRequest);

    Single<WrapperResponse<Response>> payConfirm(PayConfirmRequest payConfirmRequest);


    Single<WrapperResponse<List<FeedItem>>> getFeedList();
}

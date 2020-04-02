package com.everydev.gradual.data;

import android.content.Context;

import com.everydev.gradual.data.db.AppDatabase;
import com.everydev.gradual.data.db.model.User;
import com.everydev.gradual.data.network.RestApiHelper;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.LoginResponse;
import com.everydev.gradual.data.network.pojo.PayAgainRequest;
import com.everydev.gradual.data.network.pojo.PayConfirmRequest;
import com.everydev.gradual.data.network.pojo.PayFirstRequest;
import com.everydev.gradual.data.network.pojo.PayResponse;
import com.everydev.gradual.data.network.pojo.SignupRequest;
import com.everydev.gradual.data.network.pojo.SignupResponse;
import com.everydev.gradual.data.network.pojo.StripeKeyResponse;
import com.everydev.gradual.data.prefs.PreferencesHelper;
import com.everydev.gradual.data.utils.LoggedInMode;
import com.everydev.gradual.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class BaseDataManager implements DataManager {
    private static final String TAG = "BaseDataManager";


    private final Context mContext;
    private final AppDatabase mDatabase;
    private final PreferencesHelper mPreferencesHelper;
    private final RestApiHelper mApiHelper;

    @Inject
    public BaseDataManager(@ApplicationContext Context context,
                           AppDatabase database,
                           PreferencesHelper preferencesHelper,
                           RestApiHelper apiHelper) {
        mContext = context;
        mDatabase = database;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void setUserLoggedOut() {
        logoutUser();
    }

    @Override
    public void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode, String userName, String email, String profilePicPath) {
        mPreferencesHelper.setAccessToken(accessToken);
        mPreferencesHelper.setUserId(userId);
        mPreferencesHelper.setUserLoggedIn(loggedInMode);
        mPreferencesHelper.setUserName(userName);
        mPreferencesHelper.setUserEmail(email);
        mPreferencesHelper.setUserProfilePicUrl(profilePicPath);
    }

    @Override
    public List<User> getAll() {
        return mDatabase.userDao().getAll();
    }

    @Override
    public void insertUser(User mUser) {
        mDatabase.userDao().insertUser(mUser);
    }

    @Override
    public void insertAllUser(User... mUsersList) {
        mDatabase.userDao().insertAllUser(mUsersList);
    }

    @Override
    public void deleteUser(User mUser) {
        mDatabase.userDao().deleteUser(mUser);
    }

    @Override
    public void updateUser(User mUser) {

    }


    @Override
    public User getUserById(int uId) {
        return mDatabase.userDao().getUserById(uId);
    }

    @Override
    public List<User> loadAllByIds(int[] userIds) {
        return mDatabase.userDao().loadAllByIds(userIds);
    }

    @Override
    public User findByName(String first, String last) {
        return mDatabase.userDao().findByName(first, last);
    }

    @Override
    public Single<LoginResponse> login(LoginRequest loginRequest) {
        return mApiHelper.login(loginRequest);
    }

    @Override
    public Single<SignupResponse> signup(SignupRequest signupRequest) {
        return mApiHelper.signup(signupRequest);
    }

    @Override
    public Single<StripeKeyResponse> stripeKey() {
        return mApiHelper.stripeKey();
    }

    @Override
    public Single<PayResponse> payFirst(PayFirstRequest payFirstRequest) {
        return mApiHelper.payFirst(payFirstRequest);
    }

    @Override
    public Single<PayResponse> payAgain(PayAgainRequest payAgainRequest) {
        return mApiHelper.payAgain(payAgainRequest);
    }

    @Override
    public Single<PayResponse> payConfirm(PayConfirmRequest payConfirmRequest) {
        return mApiHelper.payConfirm(payConfirmRequest);
    }

    @Override
    public int getUserLoggedInMode() {
        return mPreferencesHelper.getUserLoggedInMode();
    }

    @Override
    public void setUserLoggedIn(LoggedInMode mode) {
        mPreferencesHelper.setUserLoggedIn(mode);
    }

    @Override
    public Long getUserId() {
        return mPreferencesHelper.getUserId();
    }

    @Override
    public void setUserId(Long userId) {
        mPreferencesHelper.setUserId(userId);
    }

    @Override
    public String getUserName() {
        return mPreferencesHelper.getUserName();
    }

    @Override
    public void setUserName(String userName) {
        mPreferencesHelper.setUserName(userName);
    }

    @Override
    public String getUserEmail() {
        return mPreferencesHelper.getUserEmail();
    }

    @Override
    public void setUserEmail(String email) {
        mPreferencesHelper.setUserEmail(email);
    }

    @Override
    public String getUserProfilePicUrl() {
        return mPreferencesHelper.getUserProfilePicUrl();
    }

    @Override
    public void setUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.getAccessToken();
    }

    @Override
    public String getUserMobile() {
        return mPreferencesHelper.getUserMobile();
    }

    @Override
    public void setUserMobile(String mobileNumber) {
        mPreferencesHelper.setUserMobile(mobileNumber);
    }

    @Override
    public boolean isCoachMarkView() {
        return mPreferencesHelper.isCoachMarkView();
    }

    @Override
    public void setCoachMarkView(boolean isShowCoachMark) {
        mPreferencesHelper.setCoachMarkView(isShowCoachMark);
    }

    @Override
    public boolean isFirstTime() {
        return mPreferencesHelper.isFirstTime();
    }

    @Override
    public void setFirstTime(boolean firstTime) {
        mPreferencesHelper.setCoachMarkView(firstTime);
    }

    @Override
    public void logoutUser() {
        mPreferencesHelper.logoutUser();
    }

    @Override
    public String getStripeKey() {
        return mPreferencesHelper.getStripeKey();
    }

    @Override
    public void setStripeKey(String stripeKey) {
        mPreferencesHelper.setStripeKey(stripeKey);
    }

    @Override
    public String getCustomerID() {
        return mPreferencesHelper.getCustomerID();
    }

    @Override
    public void setCustomerID(String customerID) {
        mPreferencesHelper.setCustomerID(customerID);
    }


}

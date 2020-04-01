package com.everydev.gradual.data;

import com.everydev.gradual.data.db.dao.UserDao;
import com.everydev.gradual.data.network.RestApiHelper;
import com.everydev.gradual.data.prefs.PreferencesHelper;
import com.everydev.gradual.data.utils.LoggedInMode;

public interface DataManager extends UserDao, PreferencesHelper, RestApiHelper {
    void updateApiHeader(Long userId, String accessToken);

    void setUserLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);
}

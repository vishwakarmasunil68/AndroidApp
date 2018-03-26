package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-03-2018.
 */

public class RequestProfilePOJO {
    @SerializedName("user_profile_detail")
    UserProfileDetailPOJO userProfileDetailPOJO;

    public UserProfileDetailPOJO getUserProfileDetailPOJO() {
        return userProfileDetailPOJO;
    }

    public void setUserProfileDetailPOJO(UserProfileDetailPOJO userProfileDetailPOJO) {
        this.userProfileDetailPOJO = userProfileDetailPOJO;
    }
}

package com.everydev.gradual.data.network.pojo;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Donee {
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("pubkey")
    private String mPubkey;

    public Long getID() {
        return mId;
    }

    public void setID(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getPubkey() {
        return mPubkey;
    }

    public void setPubkey(String pubkey) {
        mPubkey = pubkey;
    }

}

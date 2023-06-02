package org.example.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthUserInfoResponse {

    @JsonProperty("id")
    private String oauthId;

    @JsonProperty("login")
    private String userId;

    @JsonProperty("avatar_url")
    private String profileUrl;

    public OAuthUserInfoResponse() {
    }

    public OAuthUserInfoResponse(String oauthId, String userId, String profileUrl) {
        this.oauthId = oauthId;
        this.userId = userId;
        this.profileUrl = profileUrl;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public String toString() {
        return "OAuthUserInfoResponse{" +
                "oauthId='" + oauthId + '\'' +
                ", userId='" + userId + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }
}

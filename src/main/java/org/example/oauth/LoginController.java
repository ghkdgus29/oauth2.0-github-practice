package org.example.oauth;

import org.example.oauth.dto.OAuthAccessTokenRequest;
import org.example.oauth.dto.OAuthAccessTokenResponse;
import org.example.oauth.dto.OAuthUserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    private static final String getAccessCodeUrl = "https://github.com/login/oauth/authorize?client_id=Iv1.95e08d25c4e5079e";
    private static final String getAccessTokenUrl = "https://github.com/login/oauth/access_token";
    private static final String getUserInfoUrl = "https://api.github.com/user";

    @GetMapping("/login")
    public void getAccessCode(HttpServletResponse response) throws IOException {
        response.sendRedirect(getAccessCodeUrl);
    }

    @GetMapping("/callback")
    public String getAccessToken(@RequestParam String code) {
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = getAccessToken(code, restTemplate);
        OAuthUserInfoResponse user = getOauthUserInfo(restTemplate, accessToken);

        logger.info("가져온 유저 정보 = {}", user);
        return "ok";
    }

    private String getAccessToken(String code, RestTemplate restTemplate) {
        return restTemplate.postForObject(getAccessTokenUrl,
                new OAuthAccessTokenRequest(clientId, clientSecret, code),
                OAuthAccessTokenResponse.class).getAccessToken();
    }

    private OAuthUserInfoResponse getOauthUserInfo(RestTemplate restTemplate, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(getUserInfoUrl,
                HttpMethod.GET,
                request,
                OAuthUserInfoResponse.class
        ).getBody();
    }
}

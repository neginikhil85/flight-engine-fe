package com.learning.service.impl;

import com.learning.client.azureactivedirectory.AzureTokenClient;
import com.learning.service.AuthTokenService;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AzureTokenClient azureTokenClient;
    private final AzureTokenClient.OAuthClientRequestConfig oAuthClientRequestConfig;
    private final CacheManager cacheManager;

    @Override
    public String getToken() {
        String token = cacheManager.getCache("tokens").get("authToken", String.class);
        if (Objects.isNull(token) || isTokenExpired(token)) {
            token = azureTokenClient.getAuthToken(AzureTokenClient.Request.build(oAuthClientRequestConfig)).getAccessToken();
            cacheManager.getCache("tokens").put("authToken", token);
        }
        return "Bearer ".concat(token);
    }

    // TODO :: No Need for JWT dependency and parsing logic, optimise this with Time To Live in Cache
    private boolean isTokenExpired(String token) {
        try {
            return JWTParser.parse(token).getJWTClaimsSet()
                    .getExpirationTime().toInstant()
                    .isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        } catch (ParseException e) {
            log.error("[AuthTokenService] Error occurred while parsing token : {}", e.getLocalizedMessage());
            return false;
        }
    }
}

package com.learning.client.azureactivedirectory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "azure-client", url = "${oauth.client.base-url}")
public interface AzureTokenClient {

    @PostMapping(value = "/${oauth.client.auth-id}/oauth2/v2.0/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response getAuthToken(@RequestBody Request request);

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class Request {
        private String client_id;
        private String client_secret;
        private String Scope;
        private String grant_type;

        public static Request build(OAuthClientRequestConfig requestConfig) {
            return Request.builder()
                    .client_id(requestConfig.getClientId())
                    .client_secret(requestConfig.getClientSecret())
                    .Scope(requestConfig.getScope())
                    .grant_type(requestConfig.getGrantType())
                    .build();
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Response {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("ext_expires_in")
        private String extExpiresIn;
        @JsonProperty("access_token")
        private String accessToken;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "oauth.client.payload")
    @JsonIgnoreProperties(ignoreUnknown = true)
    class OAuthClientRequestConfig {
        private String clientId, clientSecret, scope, grantType;
    }
}
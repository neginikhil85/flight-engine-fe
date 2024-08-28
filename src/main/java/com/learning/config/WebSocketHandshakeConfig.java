package com.learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "websocket.handshake-url")
public class WebSocketHandshakeConfig {
    private String delay;
    private String diversion;
    private String doorClose;
    private String equipment;
    private String flightCancel;
    private String flightDelete;
    private String flightReturn;
    private String gateChange;
    private String inBlockActualTime;
    private String inBlockEstimatedTime;
    private String landingActualTime;
    private String landingEstimatedTime;
    private String offBlockActualTime;
    private String offBlockEstimatedTime;
    private String operationStatus;
    private String takeoffActualTime;
    private String takeoffEstimatedTime;
    private String terminal;
}

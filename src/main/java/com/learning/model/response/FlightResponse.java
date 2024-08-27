package com.learning.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FlightResponse {
    private boolean isDataAvailable;
    private List<FlightDocument> flightDocumentList;
    private String errorMessage;
    private int currentPage;
    private int currentPageElements;
    private int totalPages;
    private long totalPagesElements;

    @Data
    @Builder
    public static class FlightDocument {
        private String id;
        private FlightLegState flightLegState;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
    }
}


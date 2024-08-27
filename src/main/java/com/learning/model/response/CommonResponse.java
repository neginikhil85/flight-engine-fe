package com.learning.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private boolean isDataAvailable;
    private String errorMsg;
    private T data;
}

package com.example.testcontainer1.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class AppErrorResponse {

    @RequiredArgsConstructor
    @Getter
    public enum ErrorCodeConstants {
        RECORD_NOT_FOUND("RECORD_NOT_FOUND", "USER", "Unable to find the record"),
        SYSTEM_ERROR ("SYSTEM_ERROR", "SYSTEM", "Internal system errors encountered"),
        CONNECT_ERROR("CONNECT_ERROR", "SYSTEM", "Unable to connect to external service");


        final private String errorCodeName;
        final private String errorCategory;
        final private String errorDescription;

        /*
          @JsonValue is the hook to customize the output Json structure. A Map is used in this case.
         */
        @JsonValue
        public Map<String, Object> toJson() {
            return new HashMap<>() {{
                put("errorCodeName", errorCodeName);
                put("errorCategory", errorCategory);
                put("errorDescription", errorDescription);
            }};
        }
    }

    private ErrorCodeConstants errorCode;
    private String errorMessage;

    public AppErrorResponse(ErrorCodeConstants errorCodeConstants, String errorMessage) {
        this.errorCode = errorCodeConstants;
        this.errorMessage = errorMessage;
    }

}

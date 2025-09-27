package io.cklau1001.circuitbreaker1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {

    NOT_FOUND("STUDENT_NOT_FOUND", "USER","Provided student ID is not valid" ),
    COURSE_NOT_FOUND("COURSE_NOT_FOUND", "USER","Provided course ID is not valid" ),
    INTERNAL_ERROR("INTERNAL_ERROR", "SYSTEM", "Issue is found internally" );

    private final String errorCode;
    private final String errorCategory;
    private final String errorDescription;


}

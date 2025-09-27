package io.cklau1001.circuitbreaker1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomErrorResponse {

    private final String errorCode;
    private final String errorCategory;
    private final String errorDescription;
    private final String errorMessage;
    private AdditonalInfo additonalInfo;

    public CustomErrorResponse(CustomErrorCode eagle, String errorMessage) {

        this.errorCode = eagle.getErrorCode();
        this.errorCategory = eagle.getErrorCategory();
        this.errorDescription = eagle.getErrorDescription();
        this.errorMessage = errorMessage;

    }

    /*
      Create a builder pattern to accept mandatory parameters and use a setter to take optional fields
     */
    public static CustomErrorResponse builder(CustomErrorCode eagle, String errorMessage) {

        return new CustomErrorResponse(eagle, errorMessage);
    }

    public CustomErrorResponse additionalInfo(AdditonalInfo info) {

        this.additonalInfo = info;
        return this;
    }

    static public class AdditonalInfo {
        String info;
        @Setter
        String url;

        public AdditonalInfo(String info) {
            this.info = info;

        }

    }

}

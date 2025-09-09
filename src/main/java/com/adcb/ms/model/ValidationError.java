package com.adcb.ms.model;

public class ValidationError {

    public String field;
    public String code; // internal code like NOT_NULL, INVALID_FORMAT, NOT_IN_ALLOWED_VALUES, LENGTH_EXCEEDED, REQUIRED_WHEN
    public String message; // human message (EN)
    public String messageAR; // Arabic message (best-effort)
    public String vendorCode;// passthrough errorMsgCode from config

    public ValidationError() {}

    public ValidationError(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public ValidationError(String field, String code, String message, String messageAR, String vendorCode) {
        this.field = field; this.code = code; this.message = message; this.messageAR = messageAR; this.vendorCode = vendorCode;
    }

}

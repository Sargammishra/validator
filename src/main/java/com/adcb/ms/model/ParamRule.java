package com.adcb.ms.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamRule {

    private String name;
    private String displayName;
    private String displayNameAR;
    private Boolean nullable = Boolean.TRUE;
    private String validationType; // ALPHA, ALPHANUM, ALPHANUM_HYPHN, NUMERIC, NUMERIC_NEG, BENEF_FLEXI, NICKNAME_FLEXI
    private String valuesIn; // comma-separated list
    private String mandatoryIf; // pattern: otherField-VAL1,VAL2
    private Integer length; // max length
    private String errorMsgCode; // optional vendor code


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getDisplayNameAR() { return displayNameAR; }
    public void setDisplayNameAR(String displayNameAR) { this.displayNameAR = displayNameAR; }
    public Boolean getNullable() { return nullable; }
    public void setNullable(Boolean nullable) { this.nullable = nullable; }
    public String getValidationType() { return validationType; }
    public void setValidationType(String validationType) { this.validationType = validationType; }
    public String getValuesIn() { return valuesIn; }
    public void setValuesIn(String valuesIn) { this.valuesIn = valuesIn; }
    public String getMandatoryIf() { return mandatoryIf; }
    public void setMandatoryIf(String mandatoryIf) { this.mandatoryIf = mandatoryIf; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public String getErrorMsgCode() { return errorMsgCode; }
    public void setErrorMsgCode(String errorMsgCode) { this.errorMsgCode = errorMsgCode; }

}

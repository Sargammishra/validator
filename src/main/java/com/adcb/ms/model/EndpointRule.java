package com.adcb.ms.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndpointRule {

    private String name;

    @JsonProperty("validateIf")
    private String validateIf; // e.g., "regId|isDataSubmit=Y"

    @JsonProperty("param")
    private List<ParamRule> param = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getValidateIf() { return validateIf; }
    public void setValidateIf(String validateIf) { this.validateIf = validateIf; }


    public List<ParamRule> getParam() { return param; }
    public void setParam(List<ParamRule> param) { this.param = param; }
}

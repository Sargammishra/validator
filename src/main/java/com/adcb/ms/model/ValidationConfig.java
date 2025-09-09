package com.adcb.ms.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationConfig {

    @JsonIgnore
    private final Map<String, EndpointRule> endpoints = new HashMap<>();

    @JsonAnySetter
    public void addEndpoint(String name, EndpointRule rule) {
        if (rule != null) {
            rule.setName(name);
            endpoints.put(name, rule);
        }
    }


    @JsonAnyGetter
    public Map<String, EndpointRule> getEndpoints() {
        return Collections.unmodifiableMap(endpoints);
    }


    public EndpointRule get(String endpoint) {
        return endpoints.get(endpoint);
    }

}

package com.adcb.ms.service;


import com.adcb.ms.model.EndpointRule;
import com.adcb.ms.model.ValidationConfig;
import com.adcb.ms.model.ValidationError;
import com.adcb.ms.model.ValidationResult;
import com.adcb.ms.validator.ValidationEngine;
import com.adcb.ms.validator.ValidationLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ValidationService {

    @Inject
    ValidationLoader loader;
    @Inject
    ValidationEngine engine;
    @Inject
    ObjectMapper mapper;

    public ValidationResult validate(String endpoint, JsonNode payload) {
        ValidationConfig cfg = loader.load();
        EndpointRule rule = cfg.get(endpoint);
        if (rule == null) {
            return ValidationResult.withErrors(List.of(new ValidationError(
                    "endpoint","UNKNOWN_ENDPOINT","Unknown endpoint: " + endpoint, "نقطة نهاية غير معروفة: " + endpoint, null
            )));
        }
        List<ValidationError> errs = engine.validate(rule, payload);
        return ValidationResult.withErrors(errs);
    }

}

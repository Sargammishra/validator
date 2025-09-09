package com.adcb.ms.validator;


import com.adcb.ms.model.ValidationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.resource.spi.ConfigProperty;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
@Startup
    public class ValidationLoader {

    @Inject
    ObjectMapper mapper;



    public ValidationConfig load() {
        try {
            try (InputStream is =  getClass().getClassLoader().getResourceAsStream("validations/RequestValidator.json")) {
                if (is == null) throw new IllegalStateException("Rules file not found: ");
                ValidationConfig cfg = mapper.readValue(is, ValidationConfig.class);
                return cfg;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load validation rules", e);
        }
    }
}

package com.adcb.ms.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    public boolean valid;
    public List<ValidationError> errors = new ArrayList<>();

    public static ValidationResult ok() {
        ValidationResult r = new ValidationResult();
        r.valid = true; return r;
    }
    public static ValidationResult withErrors(List<ValidationError> errs) {
        ValidationResult r = new ValidationResult();
        r.valid = errs == null || errs.isEmpty();
        if (errs != null) r.errors.addAll(errs);
        return r;
    }
}

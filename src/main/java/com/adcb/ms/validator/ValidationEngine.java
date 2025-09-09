package com.adcb.ms.validator;

import com.adcb.ms.model.EndpointRule;
import com.adcb.ms.model.ParamRule;
import com.adcb.ms.model.ValidationError;
import com.adcb.ms.validator.util.Expr;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.regex.Pattern;

@ApplicationScoped
public class ValidationEngine {

    private static final Pattern ALPHA = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern ALPHANUM = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern ALPHANUM_HYPHN = Pattern.compile("^[A-Za-z0-9-]+$");
    private static final Pattern NUMERIC = Pattern.compile("^[0-9]+$");
    private static final Pattern NUMERIC_NEG = Pattern.compile("^-?[0-9]+$");
    private static final Pattern BENEF_FLEXI = Pattern.compile("^[A-Za-z0-9 .,'&()-]*$");
    private static final Pattern NICKNAME_FLEXI = Pattern.compile("^[A-Za-z0-9 .,'&()-]*$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public List<ValidationError> validate(EndpointRule rule, JsonNode payload) {
        List<ValidationError> errors = new ArrayList<>();
        if (!Expr.checkValidateIf(rule.getValidateIf(), payload)) {
            errors.add(new ValidationError(rule.getValidateIf(), "REQUIRED", rule.getValidateIf() + " is required"));
            return errors; // gated off => treat as valid (no checks)
        }
        for (ParamRule p : rule.getParam()) {
            JsonNode value = payload.get(p.getName());
            boolean present = value != null && !value.isNull() && !value.asText().isBlank();


            boolean requiredByNullable = p.getNullable() != null && !p.getNullable();
            boolean requiredByConditional = Expr.mandatoryConditionHolds(p.getMandatoryIf(), payload);
            boolean required = requiredByNullable || requiredByConditional;


            if (required && !present) {
                errors.add(new ValidationError(
                        p.getName(),
                        "REQUIRED",
                        (p.getDisplayName() != null ? p.getDisplayName() : p.getName()) + " is required",
                        (p.getDisplayNameAR() != null ? p.getDisplayNameAR() : p.getName()) + " مطلوب",
                        p.getErrorMsgCode()
                ));
                continue;
            }
            if (!present) continue; // nothing to validate further
            String text = value.asText();


// length
            if (p.getLength() != null && text.length() > p.getLength()) {
                errors.add(new ValidationError(
                        p.getName(),
                        "LENGTH_EXCEEDED",
                        String.format("%s length must be <= %d", dn(p), p.getLength()),
                        String.format("%s يجب ألا يتجاوز الطول %d", dna(p), p.getLength()),
                        p.getErrorMsgCode()
                ));
            }

            // valuesIn
            if (p.getValuesIn() != null && !p.getValuesIn().isBlank()) {
                Set<String> allowed = new HashSet<>();
                for (String v : p.getValuesIn().split(",")) allowed.add(v.trim());
                if (!allowed.contains(text)) {
                    errors.add(new ValidationError(
                            p.getName(),
                            "NOT_IN_ALLOWED_VALUES",
                            String.format("%s must be one of %s", dn(p), allowed),
                            String.format("%s يجب أن يكون واحدًا من %s", dna(p), allowed),
                            p.getErrorMsgCode()
                    ));
                }
            }


// format
            String type = Optional.ofNullable(p.getValidationType()).orElse("");
            Pattern pat = switch (type) {
                case "ALPHA" -> ALPHA;
                case "ALPHANUM" -> ALPHANUM;
                case "ALPHANUM_HYPHN" -> ALPHANUM_HYPHN;
                case "NUMERIC" -> NUMERIC;
                case "NUMERIC_NEG" -> NUMERIC_NEG;
                case "BENEF_FLEXI" -> BENEF_FLEXI;
                case "NICKNAME_FLEXI" -> NICKNAME_FLEXI;
                case "EMAIL" -> EMAIL;
                default -> null;
            };
            if (pat != null && !pat.matcher(text).matches()) {
                errors.add(new ValidationError(
                        p.getName(),
                        "INVALID_FORMAT",
                        String.format("%s has invalid format (%s)", dn(p), type),
                        String.format("%s بتنسيق غير صالح (%s)", dna(p), type),
                        p.getErrorMsgCode()
                ));
            }
        }
        return errors;
}

    private String dn(ParamRule p) { return Optional.ofNullable(p.getDisplayName()).orElse(p.getName()); }
    private String dna(ParamRule p) { return Optional.ofNullable(p.getDisplayNameAR()).orElse(p.getName()); }
}

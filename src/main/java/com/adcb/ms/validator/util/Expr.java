package com.adcb.ms.validator.util;

import com.fasterxml.jackson.databind.JsonNode;

public class Expr {
    private Expr() {}

    public static boolean checkValidateIf(String expr, JsonNode payload) {
        if (expr == null || expr.isBlank()) return true; // no gate
        String[] tokens = expr.split("\\|");
        for (String t : tokens) {
            t = t.trim();
            if (t.isEmpty()) continue;
            if (t.contains("=")) {
                String[] kv = t.split("=", 2);
                String k = kv[0].trim();
                String v = kv[1].trim();
                JsonNode n = payload.get(k);
                if (n == null) return false;
                if (!v.equals(n.asText())) return false;
            } else {
                if (!payload.has(t)) return false; // must be present
            }
        }
        return true;
    }


    /** Field-level: "otherField-VAL1,VAL2" => if otherField in {VAL1,VAL2} then required. */
    public static boolean mandatoryConditionHolds(String mandatoryIf, JsonNode payload) {
        if (mandatoryIf == null || mandatoryIf.isBlank()) return false;
        String[] parts = mandatoryIf.split("-", 2);
        if (parts.length != 2) return false;
        String field = parts[0].trim();
        String[] vals = parts[1].split(",");
        JsonNode n = payload.get(field);
        if (n == null || n.isNull()) return false;
        String actual = n.asText();
        for (String v : vals) {
            if (actual.equals(v.trim())) return true;
        }
        return false;
    }
}

package com.e_commerce.e_app.dto.product;

import com.e_commerce.e_app.exception.NotAColourException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record ProductColour(String value) {
    public ProductColour {
        Pattern colourPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
        Matcher matcher = colourPattern.matcher(value);
        if (!matcher.matches()) {
            throw new NotAColourException("Invalid colour");
        }
    }
}

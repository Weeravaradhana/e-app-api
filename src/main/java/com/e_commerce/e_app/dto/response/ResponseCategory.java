package com.e_commerce.e_app.dto.response;

import org.jilt.Builder;

import java.util.UUID;

@Builder
public record ResponseCategory(UUID publicId,
                               String name) {
}

package com.e_commerce.e_app.dto.request;

import org.jilt.Builder;

import java.util.UUID;

@Builder
public record RequestCategory(UUID publicId,
                              String name) {
}

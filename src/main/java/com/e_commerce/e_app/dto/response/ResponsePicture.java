package com.e_commerce.e_app.dto.response;

import org.jilt.Builder;

@Builder
public record ResponsePicture(byte[] file,
                              String mimeType) {
}

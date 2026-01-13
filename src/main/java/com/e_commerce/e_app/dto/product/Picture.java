package com.e_commerce.e_app.dto.product;

import org.jilt.Builder;

@Builder
public record Picture(byte[] file,String mimeType) {
}

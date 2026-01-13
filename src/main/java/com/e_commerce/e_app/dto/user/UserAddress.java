package com.e_commerce.e_app.dto.user;

import org.jilt.Builder;

@Builder
public record UserAddress(String street, String city, String zipCode, String country) {
}

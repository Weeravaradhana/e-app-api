package com.e_commerce.e_app.dto.product;

import com.e_commerce.e_app.dto.order.OrderQuantity;

public record ProductQuantity(OrderQuantity orderQuantity, ProductPublicId publicId) {
}

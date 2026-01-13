package com.e_commerce.e_app.dto.product;

import java.util.List;

public record FilterQuery(CategoryPublicId publicId, List<ProductSize> sizes) {
}

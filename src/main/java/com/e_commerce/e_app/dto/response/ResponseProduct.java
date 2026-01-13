package com.e_commerce.e_app.dto.response;

import com.e_commerce.e_app.dto.product.ProductSize;
import org.jilt.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public record ResponseProduct(String brand,
                              String colour,
                              String description,
                              String name,
                              double price,
                              ProductSize size,
                              ResponseCategory category,
                              boolean featured,
                              List<ResponsePicture> pictures,
                              UUID publicId,
                              int inStock)

{

    public void addPicture(List<ResponsePicture> newPictures) {
        List<ResponsePicture> combined = new ArrayList<>(this.pictures);
        combined.addAll(newPictures);
         new ResponseProduct(
                brand, colour, description, name, price, size, category,
                featured, combined, publicId, inStock
        );
    }



}

package com.e_commerce.e_app.repository;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.product.FilterQuery;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


public interface ProductRepository {
    Product save(Product product);
    Page<Product> findAll(Pageable pageable);
    int delete(ProductPublicId id);
    Page<Product> findAllFeaturedProduct(Pageable pageable);
    Optional<Product> findById(ProductPublicId id);
    Page<Product> findByCategoryExcludingOne(Pageable pageable,
                                                   CategoryPublicId categoryPublicId,
                                                   ProductPublicId productPublicId);
    Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery);
    void updateQuantity(ProductPublicId productPublicId, Long quantity);
}

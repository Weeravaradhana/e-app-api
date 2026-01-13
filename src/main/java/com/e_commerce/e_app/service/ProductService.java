package com.e_commerce.e_app.service;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.product.FilterQuery;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import com.e_commerce.e_app.dto.product.ProductQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Product save(Product product);
    public Page<Product> findAll(Pageable pageable);
    public int delete(ProductPublicId id);
    public Product findById(ProductPublicId id);
    public Page<Product> getFeaturedProducts(Pageable pageable);
    public Page<Product> findByCategoryExcludingOne(Pageable pageable,
                                                    ProductPublicId productPublicId,
                                                    CategoryPublicId categoryPublicId);
    public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery);
    public void updateProductQuantity(List<ProductQuantity> productQuantity);
}

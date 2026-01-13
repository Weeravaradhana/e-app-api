package com.e_commerce.e_app.service.impl;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.product.*;
import com.e_commerce.e_app.exception.ProductNotFoundException;
import com.e_commerce.e_app.repository.ProductRepository;
import com.e_commerce.e_app.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        product.setPublicId(new ProductPublicId(UUID.randomUUID()));
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public int delete(ProductPublicId id) {
        Product selectedProduct = productRepository
                .findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productRepository.delete(selectedProduct.getPublicId());
    }

    @Override
    public Product findById(ProductPublicId id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Page<Product> getFeaturedProducts(Pageable pageable) {
        return productRepository.findAllFeaturedProduct(pageable);
    }

    @Override
    public Page<Product> findByCategoryExcludingOne(Pageable pageable,
                                                    ProductPublicId productPublicId,
                                                    CategoryPublicId categoryPublicId)
    {
        Product selectedProduct = productRepository.findById(productPublicId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
       return productRepository.findByCategoryExcludingOne(pageable,
                selectedProduct.getCategory().getCategoryPublicId(),
                selectedProduct.getPublicId());
    }

    @Override
    public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery) {
        return productRepository.findByCategoryAndSize(pageable, filterQuery);
    }

    @Override
    public void updateProductQuantity(List<ProductQuantity> productQuantity) {
        productQuantity
                .forEach(product -> productRepository
                        .updateQuantity(product.publicId(),product.orderQuantity().value()));
    }


}

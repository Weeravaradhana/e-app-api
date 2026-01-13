package com.e_commerce.e_app.repository.impl;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.product.FilterQuery;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import com.e_commerce.e_app.entity.CategoryEntity;
import com.e_commerce.e_app.entity.ProductEntity;
import com.e_commerce.e_app.exception.CategoryNotFoundException;
import com.e_commerce.e_app.repository.ProductRepository;
import com.e_commerce.e_app.repository.jpa.JpaCategoryRepository;
import com.e_commerce.e_app.repository.jpa.JpaProductRepository;
import com.e_commerce.e_app.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final Mapper mapper;
    private final JpaProductRepository jpaProductRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    public ProductRepositoryImpl(Mapper mapper, JpaProductRepository jpaProductRepository, JpaCategoryRepository jpaCategoryRepository) {
        this.mapper = mapper;
        this.jpaProductRepository = jpaProductRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        ProductEntity productEntity = mapper
                .toProductEntity(product);
        productEntity
                .setCategory(jpaCategoryRepository
                        .findByPublicId(productEntity.getPublicId())
                .orElseThrow(()-> new EntityNotFoundException("Category not found")));

        mapper.toProduct(jpaProductRepository.save(productEntity));
        return mapper.toProduct(productEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return jpaProductRepository
                .findAll(pageable)
                .map(mapper::toProduct);
    }

    @Override
    @Transactional
    public int delete(ProductPublicId id) {
        ProductEntity selectedProductEntity = jpaProductRepository
                .findByPublicId(id.value())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return jpaProductRepository.deleteByPublicId(selectedProductEntity.getPublicId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAllFeaturedProduct(Pageable pageable) {
       return jpaProductRepository
               .findAllByFeaturedTrue(pageable)
               .map(mapper::toProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(ProductPublicId id) {
     return jpaProductRepository
             .findByPublicId(id.value())
             .map(mapper::toProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findByCategoryExcludingOne(Pageable pageable,
                                                    CategoryPublicId categoryPublicId,
                                                    ProductPublicId productPublicId)
    {
        return jpaProductRepository
                .findByCategoryPublicIdAndNotInProductPublicId(pageable,categoryPublicId,productPublicId)
                .map(mapper::toProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery) {
        CategoryEntity selectedCategory = jpaCategoryRepository.findByPublicId(filterQuery.publicId().value())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return jpaProductRepository.findByCategoryAndSize(
                pageable,
                selectedCategory.getPublicId(),
                filterQuery.sizes()
        ).map(mapper::toProduct);
    }

    @Override
    @Transactional
    public void updateQuantity(ProductPublicId productPublicId, Long quantity) {
        jpaProductRepository.updateQuantity(productPublicId,quantity);
    }


}

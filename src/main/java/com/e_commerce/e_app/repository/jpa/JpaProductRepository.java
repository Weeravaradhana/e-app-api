package com.e_commerce.e_app.repository.jpa;

import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import com.e_commerce.e_app.dto.product.ProductSize;
import com.e_commerce.e_app.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    int deleteByPublicId(UUID publicId);

    Optional<ProductEntity> findByPublicId(UUID publicId);

    Page<ProductEntity> findAllByFeaturedTrue(Pageable pageable);

    @Query("""
            SELECT product
            FROM ProductEntity product
            WHERE product.publicId != :productPublicId
            AND product.category.publicId = :categoryPublicId
        """)
    Page<ProductEntity> findByCategoryPublicIdAndNotInProductPublicId
            (Pageable pageable, CategoryPublicId categoryPublicId, ProductPublicId productPublicId);

    @Query("""
         SELECT product
         FROM ProductEntity product
         WHERE product.category.publicId = :categoryId
         AND (:sizes is null or product.size IN (:sizes))
      """)
    Page<ProductEntity> findByCategoryAndSize(Pageable pageable, UUID categoryId, List<ProductSize> sizes);

    @Modifying
    @Query("""
      UPDATE ProductEntity product
      SET product.inStock = product.inStock - :quantity
      WHERE product.publicId = :productPublicId
    """)
    void updateQuantity(ProductPublicId productPublicId, Long quantity);
}

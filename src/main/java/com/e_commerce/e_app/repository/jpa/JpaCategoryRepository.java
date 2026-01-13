package com.e_commerce.e_app.repository.jpa;

import com.e_commerce.e_app.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByPublicId(UUID id);
    int deleteByPublicId(UUID value);
}

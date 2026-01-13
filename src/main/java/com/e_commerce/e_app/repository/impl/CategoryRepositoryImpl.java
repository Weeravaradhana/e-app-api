package com.e_commerce.e_app.repository.impl;

import com.e_commerce.e_app.dto.product.Category;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.entity.CategoryEntity;
import com.e_commerce.e_app.exception.CategoryNotFoundException;
import com.e_commerce.e_app.repository.CategoryRepository;
import com.e_commerce.e_app.repository.jpa.JpaCategoryRepository;
import com.e_commerce.e_app.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final Mapper mapper;

    public CategoryRepositoryImpl(JpaCategoryRepository jpaCategoryRepository, Mapper mapper) {
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Category save(Category category) {
        return mapper.toCategory(jpaCategoryRepository
                .save(mapper.toCategoryEntity(category)
                )
        );
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return jpaCategoryRepository
                .findAll(pageable)
                .map(mapper::toCategory
                );
    }

    @Override
    @Transactional
    public int delete(CategoryPublicId categoryPublicId) {
        CategoryEntity selectedCategory = jpaCategoryRepository.findByPublicId(categoryPublicId.value())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return jpaCategoryRepository.deleteByPublicId(selectedCategory.getPublicId());
    }

    @Override
    public Category findByPublicId(CategoryPublicId categoryPublicId) {
        return mapper.toCategory(jpaCategoryRepository.findByPublicId(categoryPublicId.value())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found")));
    }


}

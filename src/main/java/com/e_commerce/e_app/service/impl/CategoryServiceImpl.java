package com.e_commerce.e_app.service.impl;

import com.e_commerce.e_app.dto.product.Category;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import com.e_commerce.e_app.repository.CategoryRepository;
import com.e_commerce.e_app.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        category.setCategoryPublicId(new CategoryPublicId(UUID.randomUUID()));
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public int delete(CategoryPublicId categoryPublicId) {
        return categoryRepository.delete(categoryPublicId);
    }
}

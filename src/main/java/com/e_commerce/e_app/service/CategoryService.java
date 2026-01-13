package com.e_commerce.e_app.service;

import com.e_commerce.e_app.dto.product.Category;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    public Category save(Category category);
    public Page<Category> findAll(Pageable pageable);
    public int delete(CategoryPublicId categoryId);
}

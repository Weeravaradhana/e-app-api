package com.e_commerce.e_app.api;

import com.e_commerce.e_app.dto.product.Category;
import com.e_commerce.e_app.dto.product.CategoryPublicId;
import com.e_commerce.e_app.dto.request.RequestCategory;
import com.e_commerce.e_app.dto.response.ResponseCategory;
import com.e_commerce.e_app.service.CategoryService;
import com.e_commerce.e_app.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import static com.e_commerce.e_app.api.ProductAdminController.ROLE_ADMIN;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final Mapper mapper;

    public CategoryController(CategoryService categoryService, Mapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<ResponseCategory> save(@RequestBody RequestCategory category) {
        Category savedCategory = categoryService.save(mapper.toCategory(category));
        return  ResponseEntity.ok(mapper.toCategory(savedCategory));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<Integer> delete(@PathVariable UUID publicId) {
       return ResponseEntity.ok(categoryService.delete(new CategoryPublicId(publicId)));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseCategory>> findAll(Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        Page<ResponseCategory> responseCategories = new PageImpl<>(
                categories
                        .getContent()
                        .stream()
                        .map(mapper::toCategory).toList(),
                pageable,
                categories.getTotalElements()
        );

        return ResponseEntity.ok(responseCategories);
    }
}

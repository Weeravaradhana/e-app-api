package com.e_commerce.e_app.dto.product;

import org.jilt.Builder;

@Builder
public class Category {


    private  CategoryPublicId categoryPublicId;
    private final CategoryName name;
    private Long dbId;
    private ProductPublicId publicId;


    public Category(CategoryPublicId categoryPublicId, CategoryName name, Long dbId, ProductPublicId publicId) {
        this.categoryPublicId = categoryPublicId;
        this.name = name;
        this.dbId = dbId;
        this.publicId = publicId;
    }

    public CategoryPublicId getCategoryPublicId() {
        return categoryPublicId;
    }

    public void setCategoryPublicId(CategoryPublicId categoryPublicId) {
        this.categoryPublicId = categoryPublicId;
    }

    public CategoryName getName() {
        return name;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public ProductPublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(ProductPublicId publicId) {
        this.publicId = publicId;
    }
}

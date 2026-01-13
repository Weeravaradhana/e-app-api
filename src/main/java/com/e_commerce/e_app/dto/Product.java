package com.e_commerce.e_app.dto;

import com.e_commerce.e_app.dto.product.*;
import org.jilt.Builder;

import java.util.List;

@Builder
public class Product {
    private ProductBrand brand;
    private ProductColour colour;
    private ProductDescription description;
    private ProductName name;
    private ProductPrice price;
    private ProductSize size;
    private Category category;
    private List<Picture> pictures;
    private Long dbId;
    private boolean featured;
    private ProductPublicId publicId;
    private int inStock;

    public Product(ProductBrand brand, ProductColour colour, ProductDescription description, ProductName name, ProductPrice price, ProductSize size, Category category, List<Picture> pictures, Long dbId, boolean featured, ProductPublicId publicId, int inStock) {
        this.setBrand(brand);
        this.setColour(colour);
        this.setDescription(description);
        this.setName(name);
        this.setPrice(price);
        this.setSize(size);
        this.setCategory(category);
        this.setPictures(pictures);
        this.setDbId(dbId);
        this.setFeatured(featured);
        this.setPublicId(publicId);
        this.setInStock(inStock);
    }


    public ProductBrand getBrand() {
        return brand;
    }

    public void setBrand(ProductBrand brand) {
        this.brand = brand;
    }

    public ProductColour getColour() {
        return colour;
    }

    public void setColour(ProductColour colour) {
        this.colour = colour;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public void setDescription(ProductDescription description) {
        this.description = description;
    }

    public ProductName getName() {
        return name;
    }

    public void setName(ProductName name) {
        this.name = name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public ProductPublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(ProductPublicId publicId) {
        this.publicId = publicId;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
}

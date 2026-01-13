package com.e_commerce.e_app.entity;

import com.e_commerce.e_app.dto.product.ProductSize;
import com.e_commerce.e_app.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Builder
public class ProductEntity extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "productSequence")
    @SequenceGenerator(name = "productSequence" , sequenceName = "product_sequence" , allocationSize = 1)
    private Long id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "colour")
    private String colour;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "featured")
    private boolean featured;
    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private ProductSize size;
    @Column(name = "public_id", unique = true)
    private UUID publicId;
    @Column(name = "in_stock")
    private int inStock;

    @ManyToOne
    @JoinColumn(name = "category_id" , referencedColumnName = "id")
    private CategoryEntity category;

    public ProductEntity(Long id,
                         String brand, String colour, String description, String name,
                         double price, boolean featured, ProductSize size,
                         UUID publicId, int inStock, CategoryEntity category,
                         Set<PictureEntity> pictures)
    {

        this.id = id;
        this.brand = brand;
        this.colour = colour;
        this.description = description;
        this.name = name;
        this.price = price;
        this.featured = featured;
        this.size = size;
        this.publicId = publicId;
        this.inStock = inStock;
        this.category = category;
        this.pictures = pictures;
    }

    @OneToMany(mappedBy = "product" , orphanRemoval = true , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PictureEntity> pictures;


    public ProductEntity() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
    }
}

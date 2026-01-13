package com.e_commerce.e_app.entity;

import com.e_commerce.e_app.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_category")
@Builder
public class CategoryEntity extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "categorySequence")
    @SequenceGenerator(name = "categorySequence" , sequenceName = "product_category_sequence" , allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private String name;
    private UUID publicId;
    @OneToMany(mappedBy = "category" , orphanRemoval = true)
    private Set<ProductEntity> products;

    public CategoryEntity() {
    }

    public CategoryEntity( Long id, String name,
                          UUID publicId, Set<ProductEntity> products) {
        this.id = id;
        this.name = name;
        this.publicId = publicId;
        this.products = products;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }
}

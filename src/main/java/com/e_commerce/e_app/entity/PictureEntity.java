package com.e_commerce.e_app.entity;

import com.e_commerce.e_app.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;



@Entity
@Table(name = "picture")
@Builder
public class PictureEntity extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "pictureSequence")
    @SequenceGenerator(name = "pictureSequence" , sequenceName = "product_picture_sequence" , allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Lob
    @Column(name = "file", nullable = false , columnDefinition = "MEDIUMBLOB")
    private byte[] file;
    @Column(name = "file_content_type" ,  nullable = false)
    private String mimeType;

    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "id" , nullable = false)
    private ProductEntity product;

    public PictureEntity() {

    }

    public PictureEntity(Long id, byte[] file, String mimeType, ProductEntity product) {
        this.id = id;
        this.file = file;
        this.mimeType = mimeType;
        this.product = product;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}

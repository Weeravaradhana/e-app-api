package com.e_commerce.e_app.util;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.ProductBuilder;
import com.e_commerce.e_app.dto.product.*;
import com.e_commerce.e_app.dto.request.RequestCategory;
import com.e_commerce.e_app.dto.response.*;
import com.e_commerce.e_app.entity.*;
import com.e_commerce.e_app.exception.MultipartPictureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class Mapper {

    public CategoryEntity toCategoryEntity(Category category) {
        CategoryEntityBuilder categoryEntityBuilder = new CategoryEntityBuilder();
        if(category.getDbId() != null){
            categoryEntityBuilder.id(category.getDbId());
        }
        return categoryEntityBuilder
                .name(category.getName().value())
                .publicId(category.getPublicId().value())
                .build();
    }

    public Category toCategory(CategoryEntity categoryEntity) {
        return CategoryBuilder.category()
                .dbId(categoryEntity.getId())
                .name(new CategoryName(categoryEntity.getName()))
                .publicId(new ProductPublicId(categoryEntity.getPublicId()))
                .build();
    }

    public ProductEntity toProductEntity(Product product) {
        return  ProductEntityBuilder.productEntity()
                .brand(product.getBrand().value())
                .colour(product.getColour().value())
                .description(product.getDescription().value())
                .price(product.getPrice().value())
                .name(product.getName().value())
                .size(product.getSize())
                .publicId(product.getPublicId().value())
                .featured(product.isFeatured())
                .inStock(product.getInStock())
                .category(toCategoryEntity(product.getCategory()))
                .pictures(
                        product.getPictures()
                                .stream()
                                .map(this::toPictureEntity)
                                .collect(Collectors.toSet())
                )
                .build();

    }

    private PictureEntity toPictureEntity(Picture picture) {
       return PictureEntityBuilder.pictureEntity()
                .file(picture.file())
                .mimeType(picture.mimeType())
                .build();
    }

    private List<Picture> toPicture(Set<PictureEntity> pictureEntityies) {
        return pictureEntityies.stream().map( e ->
                PictureBuilder.picture()
                        .file(e.getFile())
                        .mimeType(e.getMimeType())
                        .build()
        ).collect(Collectors.toList());
    }

    public Product toProduct(ProductEntity productEntity) {
        return ProductBuilder.product()
                .brand(new ProductBrand(productEntity.getBrand()))
                .colour(new ProductColour(productEntity.getColour()))
                .description(new ProductDescription(productEntity.getDescription()))
                .price(new ProductPrice(productEntity.getPrice()))
                .name(new ProductName(productEntity.getName()))
                .size(productEntity.getSize())
                .publicId(new ProductPublicId(productEntity.getPublicId()))
                .featured(productEntity.isFeatured())
                .inStock(productEntity.getInStock())
                .category(toCategory(productEntity.getCategory()))
                .pictures(toPicture(productEntity.getPictures()))
                .build();
    }

    public Function<MultipartFile, ResponsePicture> mapMultipartFileToResponsePicture(){
        return multipartFile -> {
            try {
                return new ResponsePicture(multipartFile.getBytes(),multipartFile.getContentType());
            } catch (IOException e) {
                throw new MultipartPictureException(String
                        .format("Cannot parse multipart file : %s",multipartFile.getOriginalFilename()));
            }
        };
    }

    public Product toProduct(ResponseProduct responseProduct) {
        ProductBuilder productBuilder = new ProductBuilder();

        if(responseProduct.publicId() != null){
            productBuilder.publicId(new ProductPublicId(responseProduct.publicId()));
        }

        if(responseProduct.pictures() != null){
            productBuilder.pictures(toPicture(responseProduct.pictures()));
        }

        return productBuilder
                .brand(new ProductBrand(responseProduct.brand()))
                .colour(new ProductColour(responseProduct.colour()))
                .description(new ProductDescription(responseProduct.description()))
                .name(new ProductName(responseProduct.name()))
                .price(new ProductPrice(responseProduct.price()))
                .size(responseProduct.size())
                .category(toCategory(responseProduct.category()))
                .featured(responseProduct.featured())
                .inStock(responseProduct.inStock())
                .build();
    }

    private Category toCategory(ResponseCategory categoryResponse) {
        return CategoryBuilder.category()
                .name(new CategoryName(categoryResponse.name()))
                .publicId(new ProductPublicId(categoryResponse.publicId()))
                .build();

    }

    private List<Picture> toPicture(List<ResponsePicture> responsePictures) {
       return responsePictures.stream().map( this::toPicture).toList();
    }

    private Picture toPicture(ResponsePicture responsePicture) {
        return PictureBuilder.picture()
                .file(responsePicture.file())
                .mimeType(responsePicture.mimeType())
                .build();
    }

    public ResponseProduct toResponseProduct(Product product) {
        return ResponseProductBuilder.responseProduct()
                .brand(product.getBrand().value())
                .colour(product.getColour().value())
                .description(product.getDescription().value())
                .price(product.getPrice().value())
                .inStock(product.getInStock())
                .name(product.getName().value())
                .featured(product.isFeatured())
                .category(toCategory(product.getCategory()))
                .size(product.getSize())
                .publicId(product.getPublicId().value())
                .pictures(toResponsePicturesList(product.getPictures()))
                .build();
    }

    public ResponseCategory toCategory(Category category){
        return ResponseCategoryBuilder.responseCategory()
                .publicId(category.getPublicId().value())
                .name(category.getName().value())
                .build();
    }

    private List<ResponsePicture> toResponsePicturesList(List<Picture> pictures) {
        return pictures.stream()
                .map(this::toResponsePicture )
                .toList();
    }

    private ResponsePicture toResponsePicture(Picture picture) {
        return ResponsePictureBuilder.responsePicture()
                .file(picture.file())
                .mimeType(picture.mimeType())
                .build();
    }

    public Category toCategory(RequestCategory categoryRequest) {
        return CategoryBuilder.category()
                .name(new CategoryName(categoryRequest.name()))
                .publicId(new ProductPublicId(categoryRequest.publicId()))
                .build();

    }

}

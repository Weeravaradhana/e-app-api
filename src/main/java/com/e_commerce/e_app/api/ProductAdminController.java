package com.e_commerce.e_app.api;

import com.e_commerce.e_app.dto.Product;
import com.e_commerce.e_app.dto.product.ProductPublicId;
import com.e_commerce.e_app.dto.response.ResponsePicture;
import com.e_commerce.e_app.dto.response.ResponseProduct;
import com.e_commerce.e_app.service.ProductService;
import com.e_commerce.e_app.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products")
public class ProductAdminController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final ProductService productService;
    private final Mapper mapper;
    private final ObjectMapper objectMapper;

    public ProductAdminController(ProductService productService, Mapper mapper, ObjectMapper objectMapper) {
        this.productService = productService;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAnyRole('" +ROLE_ADMIN+ "')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseProduct> save(MultipartHttpServletRequest request,
                                                @RequestParam("dto") String productRaw) throws JsonProcessingException {
        List<ResponsePicture> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapper.mapMultipartFileToResponsePicture())
                .toList();
        ResponseProduct responseProduct = objectMapper.readValue(productRaw, ResponseProduct.class);
        responseProduct.addPicture(pictures);
        Product product = mapper.toProduct(responseProduct);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(mapper.toResponseProduct(savedProduct));
    }

    @PreAuthorize("hasAnyRole('" +ROLE_ADMIN+ "')")
    @DeleteMapping
    public ResponseEntity<Integer> delete(@RequestParam("publicId") UUID id) {
        return ResponseEntity
                .ok(productService.delete(new ProductPublicId(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseProduct>> findAll(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        Page<ResponseProduct> responseProducts = new PageImpl<>(
                products
                        .getContent()
                        .stream()
                        .map(mapper::toResponseProduct)
                        .toList(),
                pageable,
                products.getTotalElements()
        );
        return ResponseEntity.ok(responseProducts);
    }


}

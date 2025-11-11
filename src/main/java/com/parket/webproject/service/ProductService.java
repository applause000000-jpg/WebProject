package com.parket.webproject.service;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    Long insertProduct(ProductDTO productDTO);
    List<ProductDTO> findAllProducts();
    List<ProductDTO> findProductsByUserId(Long userId);
    List<ProductDTO> findSoldProductsByUserId(Long userId);
    ProductDTO findProductById(Long productId);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(Long productId);

    default Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .productId(productDTO.getProductId())
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .realPrice(productDTO.getRealPrice())
                .author(productDTO.getAuthor())
                .publisher(productDTO.getPublisher())
                .conditions(productDTO.getConditions())
                .isSold(productDTO.getIsSold() != null ? productDTO.getIsSold() : false)
                .bookImageUrl(productDTO.getBookImageUrl() != null ? productDTO.getBookImageUrl() : null)
                .build();
        return product;
    }

    default ProductDTO entityToDto(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .productId(product.getProductId())
                .username(product.getUser().getUsername())
                .title(product.getTitle())
                .price(product.getPrice())
                .realPrice(product.getRealPrice())
                .author(product.getAuthor())
                .publisher(product.getPublisher())
                .conditions(product.getConditions())
                .isSold(product.getIsSold())
                .bookImageUrl(product.getBookImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
        return productDTO;
    }

}
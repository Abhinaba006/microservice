package com.ecom.product.service;

import com.ecom.product.dto.ProductRequest;
import com.ecom.product.dto.ProductResponse;
import com.ecom.product.models.Product;
import com.ecom.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class  ProductService {
    private final ProductRepository  productRepository;
    public ProductResponse createProduct(ProductRequest productRquest) {
        Product product = new Product();
        updateProductFromRequest(product, productRquest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);

    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        productResponse.setActive(savedProduct.getActive());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        return productResponse;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        if (productRequest.getProductName() != null) {
            product.setProductName(productRequest.getProductName());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getStockQuantity() != null) {
            product.setStockQuantity(productRequest.getStockQuantity());
        }
        if (productRequest.getImageUrl() != null) {
            product.setImageUrl(productRequest.getImageUrl());
        }
        if (productRequest.getCategory() != null) {
            product.setCategory(productRequest.getCategory());
        }
        // Assuming we keep the existing active status, or default if it's not handled via ProductRequest
        product.setActive(product.getActive() != null ? product.getActive() : true);
    }

    public ProductResponse getProductById(Long id) {
        return mapToProductResponse(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id)));
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        updateProductFromRequest(existingProduct, productRequest);
        return mapToProductResponse(productRepository.save(existingProduct));
    }

    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        existingProduct.setActive(false);
        productRepository.save(existingProduct);
//        productRepository.delete(existingProduct);
        // we will mark the product as inacive instead of deleting
    }

    public List<ProductResponse> getAllProducts() {
//        return productRepository.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String name) {
        return productRepository.searchProduct(name).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
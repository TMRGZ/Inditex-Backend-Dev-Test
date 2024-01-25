package com.inditex.myapp.domain.service.impl;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.domain.repository.ProductDetailRepository;
import com.inditex.myapp.domain.service.ExistingApiService;
import com.inditex.myapp.domain.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ExistingApiService existingApiService;

    private final ProductDetailRepository productDetailRepository;

    @Override
    public List<ProductDetail> productSimilar(String productId) {
        List<String> similarProductIds = getSimilarProductIds(productId);
        return getProductDetail(similarProductIds);
    }

    private List<String> getSimilarProductIds(String productId) {
        return existingApiService.getSimilarProducts(productId);
    }

    private ProductDetail getProductDetail(String productId) {
        return existingApiService.getProduct(productId);
    }

    private List<ProductDetail> getProductDetail(List<String> productIdList) {
        List<ProductDetail> collect = productIdList.stream()
                .map(this::getProductDetail)
                .toList();

        return productDetailRepository.saveAll(collect);
    }
}

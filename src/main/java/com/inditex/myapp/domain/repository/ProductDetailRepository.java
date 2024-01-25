package com.inditex.myapp.domain.repository;

import com.inditex.myapp.domain.model.ProductDetail;

import java.util.List;

public interface ProductDetailRepository {

    ProductDetail save(ProductDetail productDetail);

    List<ProductDetail> saveAll(List<ProductDetail> productDetailList);

}

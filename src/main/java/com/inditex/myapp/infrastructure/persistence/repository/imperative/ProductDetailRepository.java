package com.inditex.myapp.infrastructure.persistence.repository.imperative;

import com.inditex.myapp.domain.model.ProductDetail;

public interface ProductDetailRepository {

    ProductDetail save(ProductDetail productDetail);

}

package com.inditex.myapp.infrastructure.mapper;

import com.inditex.myapp.application.model.ProductDetailResponse;
import com.inditex.myapp.domain.model.ProductDetail;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface InputProductDetailMapper {

    ProductDetail map(ProductDetailResponse productDetailResponse);
}

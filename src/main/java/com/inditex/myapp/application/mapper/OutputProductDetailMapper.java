package com.inditex.myapp.application.mapper;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OutputProductDetailMapper {

    ProductDetailDto map(ProductDetail productDetail);

    Set<ProductDetailDto> map(List<ProductDetail> productDetailList);
}

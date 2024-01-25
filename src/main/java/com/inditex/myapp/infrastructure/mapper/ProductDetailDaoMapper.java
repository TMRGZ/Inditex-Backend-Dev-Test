package com.inditex.myapp.infrastructure.mapper;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.infrastructure.persistence.dao.ProductDetailDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDetailDaoMapper {

    ProductDetail map(ProductDetailDao productDetailDao);

    default List<ProductDetail> mapDao(List<ProductDetailDao> productDetail) {
        return productDetail.stream().map(this::map).toList();
    }

    @Mapping(target = "id", ignore = true)
    ProductDetailDao map(ProductDetail productDetail);

    default List<ProductDetailDao> map(List<ProductDetail> productDetail) {
        return productDetail.stream().map(this::map).toList();
    }




}

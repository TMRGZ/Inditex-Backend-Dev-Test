package com.inditex.myapp.infrastructure.mapper;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.infrastructure.persistence.dao.ProductDetailDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDetailDaoMapper {

    ProductDetail map(ProductDetailDao productDetailDao);

    @Mapping(target = "id", ignore = true)
    ProductDetailDao map(ProductDetail productDetail);

}

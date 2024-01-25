package com.inditex.myapp.infrastructure.persistence.repository;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.infrastructure.mapper.ProductDetailDaoMapper;
import com.inditex.myapp.domain.repository.ProductDetailRepository;
import com.inditex.myapp.infrastructure.persistence.repository.jpa.JpaProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    private final JpaProductDetailRepository productDetailRepository;

    private final ProductDetailDaoMapper productDetailDaoMapper;

    @Override
    public ProductDetail save(ProductDetail productDetail) {
//        log.info("saving {}", productDetail.getId());

        var dao = productDetailDaoMapper.map(productDetail);
        dao.setUpdateAt(OffsetDateTime.now());
        var savedDao = productDetailRepository.save(dao);
        return productDetailDaoMapper.map(savedDao);
    }

    @Override
    public List<ProductDetail> saveAll(List<ProductDetail> productDetailList) {
        var dao = productDetailDaoMapper.map(productDetailList);

        var savedDao = productDetailRepository.saveAll(dao);

        return productDetailDaoMapper.mapDao(savedDao);
    }
}

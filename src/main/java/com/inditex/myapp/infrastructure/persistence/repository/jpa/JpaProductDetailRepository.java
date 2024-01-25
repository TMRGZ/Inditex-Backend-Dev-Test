package com.inditex.myapp.infrastructure.persistence.repository.jpa;

import com.inditex.myapp.infrastructure.persistence.dao.ProductDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductDetailRepository extends JpaRepository<ProductDetailDao, String> {
}

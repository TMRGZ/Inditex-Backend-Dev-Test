package com.inditex.myapp.application.service;

import com.inditex.myapp.model.ProductDetail;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ProductApplicationService {

    ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId);

}
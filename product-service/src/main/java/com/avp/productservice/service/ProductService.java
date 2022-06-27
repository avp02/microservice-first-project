package com.avp.productservice.service;

import com.avp.productservice.dto.ProductRequest;
import com.avp.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(final ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}

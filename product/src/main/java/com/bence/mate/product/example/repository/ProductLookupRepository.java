package com.bence.mate.product.example.repository;

import com.bence.mate.product.example.model.ProductLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {

    ProductLookupEntity findByProductIdOrTitle(String productId, String title);
}

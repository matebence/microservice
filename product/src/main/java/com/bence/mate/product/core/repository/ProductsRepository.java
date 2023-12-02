package com.bence.mate.product.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bence.mate.product.core.data.ProductEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, String> {

    ProductEntity findByProductId(String productId);

    ProductEntity findByProductIdOrTitle(String productId, String title);
}

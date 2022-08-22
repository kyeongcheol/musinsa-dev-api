package com.musinsa.product.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.musinsa.product.category.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 DAO
 * @version 1.0
 */

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByRootCategoryId(Long id);
    Optional<ProductCategory> findByName(String korName);
}

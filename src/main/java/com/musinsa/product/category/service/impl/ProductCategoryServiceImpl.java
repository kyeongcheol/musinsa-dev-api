package com.musinsa.product.category.service.impl;

import com.musinsa.product.category.dao.ProductCategoryRepository;
import com.musinsa.product.category.dto.AddProductCategoryRequest;
import com.musinsa.product.category.dto.AddProductCategoryResponse;
import com.musinsa.product.category.dto.AllProductCategoryListRes;
import com.musinsa.product.category.dto.OneProductCategoryListRes;
import com.musinsa.product.category.entity.ProductCategory;
import com.musinsa.product.category.exception.AlreadyExistException;
import com.musinsa.product.category.exception.NotFoundException;
import com.musinsa.product.category.exception.ErrorMessage;
import com.musinsa.product.category.service.ProductCategoryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 서비스 Impl
 * @version 1.0
 */

@EnableCaching
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private final ProductCategoryRepository productCategoryRepository;
	
	/**
	 * @apiNote 상품 카테고리 전체 조회 서비스
	 */
	@Transactional(readOnly = true)
	public AllProductCategoryListRes findAllProductCategoryList() throws Exception{
		
		 List<ProductCategory> allCategoryList = productCategoryRepository.findAll();
		 categroiesExists(allCategoryList);
		 
		 List<OneProductCategoryListRes> categoryList = allCategoryList.stream()
				 .map(category -> new OneProductCategoryListRes(category.getId(), category.getName()))
                .collect(Collectors.toList());
		 
        return new AllProductCategoryListRes(categoryList);
	}
	
	@Transactional(readOnly = true)
	public AllProductCategoryListRes findOneProductCategoryList(Long id) throws Exception{
		
		categoryExistsById(id);
        List<ProductCategory> oneCategoryList = productCategoryRepository.findByRootCategoryId(id);
        categroiesExists(oneCategoryList);
        
        List<OneProductCategoryListRes> categoryList = oneCategoryList.stream()
                .map(category -> new OneProductCategoryListRes(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return new AllProductCategoryListRes(categoryList);
	}
	
	@Transactional
	public AddProductCategoryResponse addProdcutCategory(AddProductCategoryRequest request) throws Exception{
		
        categoryExistsByName(request.getName());
        if (request.getRootId() == null) { //상위 카테고리 등록
            ProductCategory saved = productCategoryRepository.save(new ProductCategory(request.getName()));
            return AddProductCategoryResponse.from(saved);
        }
        //하위 카테고리 등록
        ProductCategory rootProductCategory = productCategoryRepository.findById(request.getRootId()).orElseThrow();
        ProductCategory saved = productCategoryRepository.save((new ProductCategory(request.getName(), rootProductCategory)));
        
		return AddProductCategoryResponse.from(saved);
	}
	
    private void categroiesExists(List<ProductCategory> categoryList) {
        if(categoryList.isEmpty()){
            throw new NotFoundException();
        }
    }

    private void categoryExistsById(Long id) {
        Optional<ProductCategory> findCategory = productCategoryRepository.findById(id);
        if(!findCategory.isPresent()) {
            throw new NotFoundException();
        }
    }

    private void categoryExistsByName(String name) {
        Optional<ProductCategory> findCategory = productCategoryRepository.findByName(name);
        if (findCategory.isPresent()) {
            throw new AlreadyExistException();
		}
	}
}

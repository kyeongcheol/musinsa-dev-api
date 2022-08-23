package com.musinsa.product.category.service.impl;

import com.musinsa.product.category.dao.ProductCategoryRepository;
import com.musinsa.product.category.dto.AddProductCategoryReq;
import com.musinsa.product.category.dto.AddProductCategoryRes;
import com.musinsa.product.category.dto.AllProductCategoryListRes;
import com.musinsa.product.category.dto.DeleteProductCategoryRes;
import com.musinsa.product.category.dto.OneProductCategoryListRes;
import com.musinsa.product.category.dto.UpdateProductCategoryRes;
import com.musinsa.product.category.entity.ProductCategory;
import com.musinsa.product.category.exception.AlreadyExistException;
import com.musinsa.product.category.exception.NotFoundException;
import com.musinsa.product.category.service.ProductCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	 * @apiNote 상품 전체 카테고리 전체 조회 서비스
	 */
	
	@Cacheable("ProductCategory")
	@Transactional(readOnly = true)
	public AllProductCategoryListRes findAllProductCategoryList() throws Exception{
		
		List<ProductCategory> allCategoryList = productCategoryRepository.findAll();	// 상품 카테고리 전체 조회
		productCategoryExists(allCategoryList);		// 상품 카테고리 존재 여부 확인
		
		// 상품 카테고리 조회
		List<OneProductCategoryListRes> categoryList = allCategoryList.stream()
				.map(category -> new OneProductCategoryListRes(category.getId(), category.getName()))
				.collect(Collectors.toList());
 
		return new AllProductCategoryListRes(categoryList);
	}
	
	/**
	 * @apiNote 상품 특정 카테고리 조회 서비스
	 */
	
	@Cacheable("ProductCategory")
	@Transactional(readOnly = true)
	public AllProductCategoryListRes findOneProductCategoryList(Long id) throws Exception{
		
		productCategoryExistsById(id);
        List<ProductCategory> oneCategoryList = productCategoryRepository.findByRootCategoryId(id);
        productCategoryExists(oneCategoryList);
        
        List<OneProductCategoryListRes> categoryList = oneCategoryList.stream()
                .map(category -> new OneProductCategoryListRes(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return new AllProductCategoryListRes(categoryList);
	}
	
	/**
	 * @apiNote 상품 카테고리 등록 서비스
	 */
	
	@CacheEvict(value = "ProductCategory", allEntries = true)
	@Transactional
	public AddProductCategoryRes addProductCategory(AddProductCategoryReq request) throws Exception{
		
        categoryExistsByName(request.getName());
        if (request.getRootId() == null) { //상위 카테고리 등록
            ProductCategory saveCategory = productCategoryRepository.save(new ProductCategory(request.getName()));
            return AddProductCategoryRes.from(saveCategory);
        }
        //하위 카테고리 등록
        ProductCategory rootProductCategory = productCategoryRepository.findById(request.getRootId()).orElseThrow();
        ProductCategory saved = productCategoryRepository.save((new ProductCategory(request.getName(), rootProductCategory)));
        
		return AddProductCategoryRes.from(saved);
	}
	
	/**
	 * @apiNote 상품 카테고리 수정 서비스
	 */
	
	@CacheEvict(value = "ProductCategory", allEntries = true)
    @Transactional
    public UpdateProductCategoryRes updateProductCategory(Long id, String name) {
    	productCategoryExistsById(id);
        ProductCategory updateCategory = productCategoryRepository.findById(id).orElseThrow();
        updateCategory.updateName(name);
        return UpdateProductCategoryRes.from(updateCategory);
    }
    
    /**
     * @apiNote 상품 카테고리 삭제 서비스
     */
	
	@CacheEvict(value = "ProductCategory", allEntries = true)
    @Transactional
    public DeleteProductCategoryRes deleteProductCategory(Long id) {
    	productCategoryExistsById(id);
        ProductCategory foundCategory = productCategoryRepository.findById(id).orElseThrow();
        productCategoryRepository.delete(foundCategory);
        return DeleteProductCategoryRes.from(foundCategory);
    }
    
    /**
     * @apiNote 상품 카테고리 존재 여부 확인
     * @param categoryList
     */
    private void productCategoryExists(List<ProductCategory> categoryList) {
        if(categoryList.isEmpty()){
            throw new NotFoundException();
        }
    }
    
    /**
     * @apiNote 상품 카테고리 존재 여부 확인 (By Id)
     * @param id
     */
    private void productCategoryExistsById(Long id) {
        Optional<ProductCategory> findCategory = productCategoryRepository.findById(id);
        if(!findCategory.isPresent()) {
            throw new NotFoundException();
        }
    }
    
    /**
     * @apiNote 상품 카테고리 존재 여부 확인 (By Name)
     * @param name
     */
    private void categoryExistsByName(String name) {
        Optional<ProductCategory> findCategory = productCategoryRepository.findByName(name);
        if (findCategory.isPresent()) {
            throw new AlreadyExistException();
		}
	}
}

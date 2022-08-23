package com.musinsa.product.category.service.test;

import com.musinsa.product.category.dao.ProductCategoryRepository;
import com.musinsa.product.category.dto.AddProductCategoryReq;
import com.musinsa.product.category.dto.AddProductCategoryRes;
import com.musinsa.product.category.dto.AllProductCategoryListRes;
import com.musinsa.product.category.entity.ProductCategory;
import com.musinsa.product.category.exception.DuplicateException;
import com.musinsa.product.category.exception.NotFoundException;
import com.musinsa.product.category.service.ProductCategoryService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductCategoryService Integration Test")
@SpringBootTest
@Transactional
class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Nested
    @DisplayName("Find Product Category Test")
    class findProductCategoryTest {
    	
    	/**
    	 * @implNote 전체 상품 카테고리 조회
    	 * @throws Exception 
    	 */
        @Test
        void findAllProductCategoryList() throws Exception {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
        	
        	ProductCategory subProductCategory1 = new ProductCategory("반팔", rootProductCategory1);
        	ProductCategory subProductCategory2 = new ProductCategory("긴팔", rootProductCategory1);
        	productCategoryRepository.save(subProductCategory1);
        	productCategoryRepository.save(subProductCategory2);

        	AllProductCategoryListRes allProductCategoryListRes = productCategoryService.findAllProductCategoryList();
            List categoryList = allProductCategoryListRes.getAllProductCategoryRes();

            //then
            assertThat(categoryList.size()).isEqualTo(3);
        }
        
        /**
         * @throws Exception 
         * @implNote 특정 상품 카테고리 조회
         */
        @Test
        void findOneProductCategoryList() throws Exception {

        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
        	
        	ProductCategory subProductCategory1 = new ProductCategory("반팔", rootProductCategory1);
        	ProductCategory subProductCategory2 = new ProductCategory("긴팔", rootProductCategory1);
        	productCategoryRepository.save(subProductCategory1);
        	productCategoryRepository.save(subProductCategory2);
        	
            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();

            //when
            AllProductCategoryListRes allProductCategoryListRes = 
            		productCategoryService.findOneProductCategoryList(rootId1);
            List categoryList = allProductCategoryListRes.getAllProductCategoryRes();

            //then
            assertThat(categoryList.size()).isEqualTo(2);
        }
        
        /**
         * @implNote 전체 상품 카테고리 조회 시 없을 경우 예외 처리
         * @throws Exception
         */
        @Test
        void notFoundAllProductCategoryException() throws Exception {

        	NotFoundException thrown = assertThrows(NotFoundException.class,
                    () -> productCategoryService.findAllProductCategoryList());
            assertEquals("상품 카테고리가 존재하지 않습니다.", thrown.getMessage());
        }
        
        /**
         * @implNote 상위 상품 카테고리 조회 시 없을 경우 예외 처리
         * @throws Exception
         */
        @Test
        void notFoundRootProductCategoryException() throws Exception {
        	
        	NotFoundException thrown = assertThrows(NotFoundException.class,
                    () -> productCategoryService.findOneProductCategoryList(1L));
            assertEquals("상위 상품 카테고리가 존재하지 않습니다.", thrown.getMessage());
        }
        
        /**
         * @implNote 하위 상품 카테고리 조회 시 없을 경우 예외 처리
         * @throws Exception
         */
        @Test
        void notFoundSubProductCategoryException() throws Exception {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();

            NotFoundException thrown = assertThrows(NotFoundException.class,
                    () -> productCategoryService.findOneProductCategoryList(rootId1));
            assertEquals("하위 상품 카테고리가 존재하지 않습니다.", thrown.getMessage());
        }

    }

    @Nested
    @DisplayName("Add Product Category Test")
    class addProductCategoryTest {
    	
    	/**
    	 * @throws Exception 
    	 * @implNote 상위 카테고리 등록
    	 */
        @Test
        void addRootProductCategory() throws Exception {
        	
            AddProductCategoryReq request1 = new AddProductCategoryReq("상의", null);
            AddProductCategoryReq request2 = new AddProductCategoryReq("하의", null);
            AddProductCategoryReq request3 = new AddProductCategoryReq("신발", null);

            AddProductCategoryRes category1 = productCategoryService.addProductCategory(request1);
            AddProductCategoryRes category2 = productCategoryService.addProductCategory(request2);
            AddProductCategoryRes category3 = productCategoryService.addProductCategory(request3);

            assertThat(productCategoryRepository.findByName(category1.getName()).orElseThrow().getName()).isEqualTo(request1.getName());
            assertThat(productCategoryRepository.findByName(category2.getName()).orElseThrow().getName()).isEqualTo(request2.getName());
            assertThat(productCategoryRepository.findByName(category3.getName()).orElseThrow().getName()).isEqualTo(request3.getName());
        }
        
        /**
         * @throws Exception 
         * @implNote 하위 카테고리 등록
         */
        @Test
        void addSubProductCategory() throws Exception {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	ProductCategory rootProductCategory2 = new ProductCategory("하의");
        	productCategoryRepository.save(rootProductCategory1);
        	productCategoryRepository.save(rootProductCategory2);

            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();
            Long rootId2 = productCategoryRepository.findByName("하의").orElseThrow().getId();
            AddProductCategoryReq request1 = new AddProductCategoryReq("반팔", rootId1);
            AddProductCategoryReq request2 = new AddProductCategoryReq("청바지", rootId2);

            AddProductCategoryRes category1 = productCategoryService.addProductCategory(request1);
            AddProductCategoryRes category2 = productCategoryService.addProductCategory(request2);

            assertThat(productCategoryRepository.findByName(category1.getName()).orElseThrow().getName()).isEqualTo(request1.getName());
            assertThat(productCategoryRepository.findByName(category2.getName()).orElseThrow().getName()).isEqualTo(request2.getName());
        }
        
        /**
         * @implNote 상위 상품 카테고리 등록 시 중복 예외 처리 
         * @throws Exception
         */
        @Test
        void dupRootProductCategoryException() throws Exception {
        	
        	AddProductCategoryReq request1 = new AddProductCategoryReq("상의", null);
        	AddProductCategoryReq request2 = new AddProductCategoryReq("상의", null);

        	productCategoryService.addProductCategory(request1);

            DuplicateException thrown = assertThrows(DuplicateException.class, () -> productCategoryService.addProductCategory(request2));
            assertEquals("등록된 상위 상품 카테고리가 존재합니다.", thrown.getMessage());
        }
        
        /**
         * @implNote 하위 상품 카테고리 등록 시 중복 예외 처리
         * @throws Exception
         */
        @Test
        void dupSubProductCategoryException() throws Exception {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);

            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();
            Long rootId2 = productCategoryRepository.findByName("상의").orElseThrow().getId();
            AddProductCategoryReq request1 = new AddProductCategoryReq("반팔", rootId1);
            AddProductCategoryReq request2 = new AddProductCategoryReq("반팔", rootId2);

            productCategoryService.addProductCategory(request1);

            DuplicateException thrown = assertThrows(DuplicateException.class, () -> productCategoryService.addProductCategory(request2));
            assertEquals("등록된 하위 상품 카테고리가 존재합니다.", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Update Product Category Test")
    class updateProductCategoryTest {
    	
    	/**
    	 * @apiNote 상품 카테고리 이름 수정
    	 */
        @Test
        void updateProductCategoryName() {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();

            productCategoryService.updateProductCategory(rootId1, "하의");

            assertThat(productCategoryRepository.findById(rootId1).orElseThrow().getName()).isEqualTo("하의");
        }
        
        /**
         * @apiNote 상품 카테고리 이름 수정 시 상품 카테고리가 없을 경우 예외 처리
         * @throws Exception
         */
        @Test
        void notFoundUpdateProductCategoryException() throws Exception {

            //then
            NotFoundException thrown = assertThrows(NotFoundException.class,
                    () -> productCategoryService.updateProductCategory(1L, "의류"));
            assertEquals("수정하려는 상품 카테고리가 존재하지 않습니다.", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Delete Product Category Test")
    class deleteProductCategoryTest {
    	
    	/**
    	 * @apiNote 상위 상품 카테고리 삭제
    	 */
        @Test
        void deleteRootProductCategory() {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
        	
        	ProductCategory subProductCategory1 = new ProductCategory("반팔", rootProductCategory1);
        	productCategoryRepository.save(subProductCategory1);
        	
            Long rootId1 = productCategoryRepository.findByName("상의").orElseThrow().getId();

            productCategoryService.deleteProductCategory(rootId1);

            assertThat(productCategoryRepository.findByName("상의").isPresent()).isEqualTo(false);
        }
        
        /**
         * @apiNote 하위 상품 카테고리 삭제
         */
        @Test
        void deleteSubProductCategory() {
        	
        	ProductCategory rootProductCategory1 = new ProductCategory("상의");
        	productCategoryRepository.save(rootProductCategory1);
        	
        	ProductCategory subProductCategory1 = new ProductCategory("반팔", rootProductCategory1);
        	productCategoryRepository.save(subProductCategory1);
        	
            Long subId1 = productCategoryRepository.findByName("반팔").orElseThrow().getId();

            productCategoryService.deleteProductCategory(subId1);

            assertThat(productCategoryRepository.findByName("반팔").isPresent()).isEqualTo(false);
        }
        
        /**
         * @apiNote 상품 카테고리 삭제 시 없을 경우 예외 처리
         * @throws Exception
         */
        @Test
        void notFoundDeleteProductCategoryException() throws Exception {
            
            NotFoundException thrown = assertThrows(NotFoundException.class,
                    () -> productCategoryService.deleteProductCategory(1L));
            assertEquals("삭제하려는 상품 카테고리가 존재하지 않습니다.", thrown.getMessage());
        }
    }
}

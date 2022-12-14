package com.musinsa.product.category.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.product.category.controller.ProductCategoryController;
import com.musinsa.product.category.dto.AddProductCategoryReq;
import com.musinsa.product.category.dto.AddProductCategoryRes;
import com.musinsa.product.category.dto.AllProductCategoryListRes;
import com.musinsa.product.category.dto.DeleteProductCategoryRes;
import com.musinsa.product.category.dto.OneProductCategoryListRes;
import com.musinsa.product.category.dto.UpdateProductCategoryRes;
import com.musinsa.product.category.entity.ProductCategory;
import com.musinsa.product.category.service.ProductCategoryService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("ProductCategoryControllerClass")
@WebMvcTest(ProductCategoryController.class)
@AutoConfigureMockMvc()
class ProductCategoryControllerTest {
	
	private static final String API_URL = "/api/product/category";
	
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductCategoryService productCategoryService;
    
    @Nested
    @DisplayName("ProductCategoryController Unit Test")
    class ControllerTest{
    	
    	/**
    	 * @implNote ?????? ??????????????? ???????????? ?????? -> ?????? ?????? ???????????? ??????
    	 * @throws Exception
    	 */
    	@Test
    	void productCategoryExists_findAllProductCategoryList() throws Exception{
    		
    		ProductCategory productCategory = new ProductCategory("??????");
    		OneProductCategoryListRes oneProductCategoryListRes = 
    				new OneProductCategoryListRes(productCategory.getId(), productCategory.getName());
    		
    		List<OneProductCategoryListRes> categoryList = new ArrayList<>();
    		categoryList.add(oneProductCategoryListRes);
    		
    		AllProductCategoryListRes<List<OneProductCategoryListRes>> allProdcutCategoryList = 
    				new AllProductCategoryListRes(categoryList);
            
            given(productCategoryService.findAllProductCategoryList()).willReturn(allProdcutCategoryList);

            mvc.perform(get(API_URL)
            		.contentType(MediaType.APPLICATION_JSON))
            		.andExpect(status().isOk())
            		.andExpect(jsonPath("$.allProductCategoryRes[0].name").value("??????"));
    	}
    	
    	/**
    	 * @implNote ?????? ?????? ??????????????? ???????????? ?????? -> ?????? ?????? ???????????? ??????
    	 * @throws Exception
    	 */
    	@Test
    	void productCategoryExistsById_findOneProductCategoryList() throws Exception{
    		
    		Long rootId = 1L;
            Long id = 2L;
            
            AddProductCategoryReq addProductCategoryReq = new AddProductCategoryReq("??????", rootId);
            productCategoryService.addProductCategory(addProductCategoryReq);
            
            OneProductCategoryListRes oneProductCategoryListRes = new OneProductCategoryListRes(id, "??????");
            List<OneProductCategoryListRes> categoryList = new ArrayList<>();
            categoryList.add(oneProductCategoryListRes);
            
            AllProductCategoryListRes<List<OneProductCategoryListRes>> allProdcutCategoryList = 
    				new AllProductCategoryListRes(categoryList);
            
            given(productCategoryService.findOneProductCategoryList(rootId)).willReturn(allProdcutCategoryList);
            
    		mvc.perform(get(API_URL + "/" + rootId)
    				.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.allProductCategoryRes[0].name").value("??????"));
    	}
    	
    	/**
    	 * @implNote ????????? ?????? ??????????????? ???????????? ?????? ?????? -> ?????? ???????????? ??????
    	 * @throws Exception
    	 */
    	@Test
    	void categoryExistsByName_addProductCategory() throws Exception{
    		
    		Long rootId = null;
    		
    		AddProductCategoryReq addProductCategoryReq = new AddProductCategoryReq("????????????", null);
    		ObjectMapper mapper = new ObjectMapper();
    		String reqBody = mapper.writeValueAsString(addProductCategoryReq);
            AddProductCategoryRes addProductCategoryRes = new AddProductCategoryRes(1L, "????????????");
            
            given(productCategoryService.addProductCategory(any(AddProductCategoryReq.class))).willReturn(addProductCategoryRes);
            
            ResultActions resultActions = mvc.perform(post(API_URL)
            		.content(reqBody).contentType(MediaType.APPLICATION_JSON));
            
            resultActions.andExpect(status().isCreated())
            			.andExpect(jsonPath("$.id").value(1L))
            			.andExpect(jsonPath("$.name").value("????????????"));
            
    	}
    	
    	/**
    	 * @implNote ????????? ?????? ???????????? ????????? ?????? -> ?????? ???????????? ?????? ??????
    	 * @throws Exception
    	 */
    	@Test
    	void productCategoryExistsById_updateProductCategory() throws Exception{
    		
    		Long id = 1L;
            Long parentId = null;
            String updateName = "????????????";
            initalProductCategory(parentId);
            
            UpdateProductCategoryRes updateProductCategoryRes = new UpdateProductCategoryRes(id, updateName);
            
            given(productCategoryService.updateProductCategory(id, updateName)).willReturn(updateProductCategoryRes);
            
            ResultActions resultActions = mvc.perform(patch(API_URL + "/" + id + "?name=" + updateName));
            
            resultActions.andExpect(status().isOk())
            			.andExpect(jsonPath("$.id").value(1L))
            			.andExpect(jsonPath("$.name").value("????????????"));
    	}
    	
    	@Test
    	void productCategoryExistsById_deleteProductCategory() throws Exception{
            
            Long id = 1L;
            Long parentId = null;
            initalProductCategory(parentId);
            DeleteProductCategoryRes deleteProductCategoryRes = new DeleteProductCategoryRes(id);

            //when
            given(productCategoryService.deleteProductCategory(id)).willReturn(deleteProductCategoryRes);

            //then
            ResultActions resultActions = mvc.perform(delete(API_URL + "/" +id));
            		
            resultActions.andExpect(status().isOk())
            			.andExpect(jsonPath("$.id").value(1L));
    	}
    	
    	private void initalProductCategory(Long rootId) throws Exception {
    		AddProductCategoryReq addProductCategoryReq = new AddProductCategoryReq("??????", rootId);
    		productCategoryService.addProductCategory(addProductCategoryReq);
        }
    }
}

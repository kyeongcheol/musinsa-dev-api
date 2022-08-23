package com.musinsa.product.category.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 Entity
 */
@Getter
@Entity
@Table(name="product_category")
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 64, nullable = false)
	private String name;
	
	public ProductCategory(String name, ProductCategory rootCategory) {
        this.name = name;
        this.rootCategory = rootCategory;
    }

    public ProductCategory(String name) {
        this.name = name;
    }

    protected ProductCategory() {
    }
	    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "root_category_id")
	private ProductCategory rootCategory;

	@OneToMany(mappedBy = "rootCategory", cascade = CascadeType.REMOVE)
	private List<ProductCategory> subCategory = new ArrayList<>();
	
	public void updateName(String name) {
        this.name = name;
    }
}

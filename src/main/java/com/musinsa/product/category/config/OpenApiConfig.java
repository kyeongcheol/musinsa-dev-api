package com.musinsa.product.category.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "상품 카테고리 API 명세서",
        description = "상품 카테고리 API 명세서",
        version = "v1",
        contact = @Contact(name = "kcyang", email = "ykc90831@gmail.com"),
        license = @License(name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html")
    )
)
@Configuration
public class OpenApiConfig {
	
  /**
   * productCategoryApi.
   * @return GroupedOpenApi
   */
  @Bean
  public GroupedOpenApi productCategoryApi() {
    String[] paths = {"/api/**"};
    return GroupedOpenApi.builder().group("상품 카테고리 API")
    		.pathsToMatch(paths)
    		.build();
  }

}

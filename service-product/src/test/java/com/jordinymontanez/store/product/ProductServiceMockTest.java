package com.jordinymontanez.store.product;

import com.jordinymontanez.store.product.entity.Category;
import com.jordinymontanez.store.product.entity.Product;
import com.jordinymontanez.store.product.repository.ProductRepository;
import com.jordinymontanez.store.product.service.ProductService;
import com.jordinymontanez.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService =new ProductServiceImpl(productRepository);

        Product product= Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
    }

    @Test
    public void whenValidGetID_ThenReturnProduct(){
        Product found=productService.getProduct(1L);

        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Product product=productService.updateStock(1L,Double.parseDouble("8"));

        Product found=productService.getProduct(1L);
        Assertions.assertThat(found.getStock()).isEqualTo(18);
    }
}

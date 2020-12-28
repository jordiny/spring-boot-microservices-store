package com.jordinymontanez.store.product;

import com.jordinymontanez.store.product.entity.Category;
import com.jordinymontanez.store.product.entity.Product;
import com.jordinymontanez.store.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct(){
        Product product= Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();

        productRepository.save(product);

        List<Product> products= productRepository.findByCategory(product.getCategory());

        Assertions.assertThat(products.size()).isEqualTo(3);

    };
}

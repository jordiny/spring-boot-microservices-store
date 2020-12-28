package com.jordinymontanez.store.product.repository;

import com.jordinymontanez.store.product.entity.Category;
import com.jordinymontanez.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    public List<Product> findByCategory(Category category);
    public Product findByName(String name);
}

package com.jordinymontanez.store.product.service;

import com.jordinymontanez.store.product.entity.Category;
import com.jordinymontanez.store.product.entity.Product;
import com.jordinymontanez.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
 @RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

   // @Autowired //inyeccion de dependencias
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {

        Product productDb=productRepository.findByName(product.getName());
        if(productDb!=null){
            return  productDb;
        }
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDb=getProduct(product.getId());
        if(productDb==null){
            return  null;
        }
        productDb.setName(product.getName());
        productDb.setDescription(product.getDescription());
        productDb.setCategory(product.getCategory());
        productDb.setPrice(product.getPrice());
        return productRepository.save(productDb);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDb=getProduct(id);
        if(productDb==null){
            return  null;
        }
        productDb.setStatus("DELETED");
        return productRepository.save(productDb);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDb = getProduct(id);
        if (productDb == null) {
            return null;
        }
        productDb.setStock(productDb.getStock() + quantity);
        return productRepository.save(productDb);
    }
}

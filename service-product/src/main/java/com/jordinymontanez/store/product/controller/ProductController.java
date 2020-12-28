package com.jordinymontanez.store.product.controller;

import com.jordinymontanez.store.product.entity.Category;
import com.jordinymontanez.store.product.entity.Product;
import com.jordinymontanez.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products=new ArrayList<>();
        if(categoryId==null){
            products=productService.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            products=productService.findByCategory(Category.builder().id(categoryId).build());

            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id){
        Product product=productService.getProduct(id);
        if(product==null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Product productCreate=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id")Long id,@RequestBody Product product){
        product.setId(id);
        Product productDb=productService.updateProduct(product);

        if(productDb==null){
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id")Long id){
        Product productDb=productService.deleteProduct(id);

        if(productDb==null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable(value = "id")Long id, @RequestParam(name = "quantity", required = true) Double quantity){

        Product productDb=productService.updateStock(id,quantity);

        if(productDb==null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> messages=result
                .getFieldErrors()
                .stream()
                .map(err -> {
                    Map<String,String> error=new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return  error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage=ErrorMessage.builder()
                .code("01")
                .messages(messages)
                .build();

        ObjectMapper mapper=new ObjectMapper();
        String jsonString="";
        try {
            jsonString=mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return  jsonString;
    }
}

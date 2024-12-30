package com.example.ProductsStockManagement.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() ->  new NoSuchElementException("No Product present with the given id"));
    }
}

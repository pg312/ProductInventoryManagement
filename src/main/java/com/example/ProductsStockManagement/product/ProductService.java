package com.example.ProductsStockManagement.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
}

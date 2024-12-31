package com.example.ProductsStockManagement.product;

import com.example.ProductsStockManagement.product.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Product updateProduct(Optional<Integer> productId, Product product)  {
        if(product.getId() == 0){
            throw new InvalidInputException("Product Id is missing in the request");
        }
        if(productId.get() != product.getId()) {
            throw new InvalidInputException("Id provided in the URL is different from Product that is sent to update");
        }
        productId.orElseThrow(() -> new NoSuchElementException("No Product is present with the given id"));
        return productRepository.save(product);
    }
}

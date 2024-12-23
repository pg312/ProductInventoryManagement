package com.example.ProductsStockManagement.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Product> saveProducts(@RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getProducts(){
        return new ResponseEntity<>("Hello",HttpStatus.OK);
    }
}

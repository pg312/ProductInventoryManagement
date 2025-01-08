package com.example.ProductsStockManagement.product;


import com.example.ProductsStockManagement.product.exceptions.InvalidInputException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Product> saveProducts(@Valid @RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct,HttpStatus.OK);
    }

    @GetMapping(value = {"","/{id}"})
    public ResponseEntity<List<Product>> getProducts(@PathVariable(required = false) Optional<Integer> id){
        List<Product> allProducts = new ArrayList<>();
        if(id.isPresent()){
            allProducts.add(productService.findById(id.get()));
        }else{
            allProducts = productService.getAllProducts();
        }
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Optional<Integer> id, @RequestBody Product product){
        Product updatedProduct = productService.updateProduct(id, product);
        return  new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Optional<Integer> id){
        id.ifPresent((var productId) -> productService.deleteProduct(productId));
        return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
    }

}

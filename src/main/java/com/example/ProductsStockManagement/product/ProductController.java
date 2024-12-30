package com.example.ProductsStockManagement.product;


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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception){
        String errorMessage = exception.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
}

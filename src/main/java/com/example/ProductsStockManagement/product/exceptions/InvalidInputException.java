package com.example.ProductsStockManagement.product.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message){
        super(message);
    }
}

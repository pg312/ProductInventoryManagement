package com.example.ProductsStockManagement.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@Data
public class  Product {

    @Id
    @GeneratedValue
    public int id;

    @Column
    public String name;

    @Column
    public String sku;
    @Column
    public int price;

    public Product(String name, String sku, int price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }
}

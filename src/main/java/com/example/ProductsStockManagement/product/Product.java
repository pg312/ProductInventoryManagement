package com.example.ProductsStockManagement.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class  Product {

    @Id
    @GeneratedValue
    public int id;

    @Column
    @NotNull(message = "Name can't be null")
    public String name;

    @Column
    @NotNull(message = "SKU can't be null")
    public String sku;

    @Column
    @Min(value = 1, message = "Price can't be null")
    public int price;

    public Product(String name, String sku, int price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }
}

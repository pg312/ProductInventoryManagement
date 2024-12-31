package com.example.ProductsStockManagement.integrationtests;

import com.example.ProductsStockManagement.ProductsStockManagementApplication;
import com.example.ProductsStockManagement.product.Product;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductsStockManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testSaveProduct() throws JSONException {

        Product product =new Product("product1","sku1",120);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        JSONObject responseObject = new JSONObject(response.getBody());

        assertNotNull(responseObject.get("id"));
        assertEquals("product1", responseObject.get("name"));
        assertEquals("sku1", responseObject.get("sku"));
        assertEquals(120, responseObject.get("price"));

    }

    @Test
    public void testSaveProductNameNotNullValidation()  {
        Product product =new Product(null,"sku1",120);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

       assertEquals("[\"Name can't be null\"]", response.getBody().lines().findFirst().get());
       assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void testSaveProductSKUNotNullValidation(){
        Product product =new Product("product1",null,120);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        assertEquals("[\"SKU can't be null\"]", response.getBody().lines().findFirst().get());
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void testSaveProductPriceNotNullValidation(){
        Product product =new Product("product1","sku1",0);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        assertEquals("[\"Price can't be null\"]", response.getBody().lines().findFirst().get());
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void testGetAllProducts()  {
        Product product =new Product("product1","sku1",100);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.GET,entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetProductById()  {
        Product product =new Product("product1","sku1",100);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products/1"),
                HttpMethod.GET,entity, String.class);
        assertNotNull(response.getBody());
        assertEquals("[{\"id\":1,\"name\":\"product1\",\"sku\":\"sku1\",\"price\":100}]", response.getBody());
    }

    @Test
    public void testThrowsExceptionWhenProductNotFound() throws JSONException {
        Product product =new Product("product1","sku1",100);

        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);

        restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);



        ResponseEntity<String> response = restTemplate.exchange(
                            createURLWithPort("/products/3"),
                            HttpMethod.GET,entity, String.class);

        assertNotNull(response.getBody());
        assertEquals("No Product present with the given id", response.getBody());

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

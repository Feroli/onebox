package org.onebox.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebox.model.Product;
import org.onebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    private Product firstProduct;
    private Product secondProduct;

    @BeforeEach
    void setUp() {
        productService.deleteAllProducts();
        firstProduct = productService.createProduct("Product 1", 10.0);
        secondProduct = productService.createProduct("Product 2", 15.0);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("["
                        + generateProductAsJson(firstProduct.getId(), firstProduct.getDescription(), firstProduct.getAmount()) +","
                        + generateProductAsJson(secondProduct.getId(), secondProduct.getDescription(), secondProduct.getAmount()) +"]"));
    }

    @Test
    @Transactional
    void getProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(generateProductAsJson(firstProduct.getId(), firstProduct.getDescription(), firstProduct.getAmount())));
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generateProductAsJson(3L, "New Product", 25.0)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String generateProductAsJson(Long id, String description, double amount) {
        return "{\"id\":"+id+",\"description\":\""+description+"\",\"amount\":"+amount+"}";
    }
}
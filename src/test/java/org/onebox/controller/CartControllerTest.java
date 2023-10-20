package org.onebox.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebox.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService.deleteAllCarts();
    }

    @Test
    @Transactional
    void createCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/carts"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    void getCartById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carts"))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        int cartId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/" + cartId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void deleteCart() throws Exception {
        MvcResult cartCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/carts"))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        int cartId = JsonPath.read(cartCreationResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/" + cartId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    void addProductToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/carts"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String productJson = "{\"description\":\"Product A\",\"amount\":20.0}";
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/1/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


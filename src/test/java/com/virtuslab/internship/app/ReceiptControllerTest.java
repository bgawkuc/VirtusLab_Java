package com.virtuslab.internship.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateReceiptWithNoDiscounts() throws Exception {
        var productDb = new ProductDb();
        var basket = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price()).add(apple.price());

        basket.addProduct(milk);
        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(apple);
        ReceiptController.createReceipt(basket);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(basket);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/receipt")
                        .contentType("application/json;charset=UTF-8")
                        .content(string))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discounts").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(expectedTotalPrice.stripTrailingZeros()));
    }

    @Test
    public void shouldCreateReceiptWith10PercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var basket = new Basket();
        var milk = productDb.getProduct("Milk");
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(25)).multiply(BigDecimal.valueOf(0.9));

        for (int i = 0; i < 25; i++) {
            basket.addProduct(milk);
        }
        ReceiptController.createReceipt(basket);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(basket);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/receipt")
                        .contentType("application/json;charset=UTF-8")
                        .content(string))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discounts", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(expectedTotalPrice.stripTrailingZeros()));
    }

    @Test
    public void shouldCreateReceiptWith15PercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var basket = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");

        var expectedTotalPrice = milk.price().add(bread.price()).add(bread.price()).add(cereals.price()).multiply(BigDecimal.valueOf(0.85));

        basket.addProduct(bread);
        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(cereals);

        ReceiptController.createReceipt(basket);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(basket);


        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/receipt")
                        .contentType("application/json;charset=UTF-8")
                        .content(string))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discounts", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(expectedTotalPrice.stripTrailingZeros()));
    }

    @Test
    public void shouldCreateReceiptWith10And15PercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var basket = new Basket();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(12)).add(cereals.price()).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));


        for (int i = 0; i < 12; i++) {
            basket.addProduct(bread);
        }
        basket.addProduct(cereals);
        ReceiptController.createReceipt(basket);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(basket);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/receipt")
                        .contentType("application/json;charset=UTF-8")
                        .content(string))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discounts", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(expectedTotalPrice.stripTrailingZeros()));
    }
}
package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenPercentIDiscountTest {

    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50() {
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        List<String> discountsList = new ArrayList<>();
        var receipt = new Receipt(receiptEntries, discountsList);
        var expectedTotalPrice = cheese.price().add(steak.price()).multiply(BigDecimal.valueOf(0.9));

//         When
        var receiptAfterDiscount = DiscountManager.apply10PercentDiscount(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply10PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        List<String> discountsList = new ArrayList<>();
        var receipt = new Receipt(receiptEntries, discountsList);
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount = DiscountManager.apply10PercentDiscount(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}

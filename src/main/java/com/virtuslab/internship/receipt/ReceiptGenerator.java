package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import java.util.ArrayList;
import java.util.List;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discountsList = new ArrayList<>();
        int newQuantity;

        for (int i = 0; i < basket.getProducts().size(); i++) {
            Product basketProduct = basket.getProducts().get(i);
            newQuantity = 1;

            for (int j = 0; j < receiptEntries.size(); j++) {
                ReceiptEntry element = receiptEntries.get(j);

                if (element.product().name().equals(basketProduct.name())) {
                    newQuantity = element.quantity() + 1;
                    ReceiptEntry newElement = new ReceiptEntry(basketProduct, newQuantity);
                    receiptEntries.set(j, newElement);
                }
            }

            if (newQuantity == 1) {
                ReceiptEntry newElement = new ReceiptEntry(basketProduct, newQuantity);
                receiptEntries.add(newElement);
            }
        }
        return new Receipt(receiptEntries, discountsList);
    }
}

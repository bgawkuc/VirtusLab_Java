package com.virtuslab.internship.app;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountManager;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {

    @RequestMapping("/receipt")
    @PostMapping(value = "/receipt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public static Receipt createReceipt(@RequestBody Basket basket) {
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        Receipt receipt = receiptGenerator.generate(basket);
        receipt = DiscountManager.applyDiscounts(receipt);

        return receipt;
    }

}

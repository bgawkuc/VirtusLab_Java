package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class FifteenPercentDiscount extends AbstractDiscount {
    public static String NAME = "FifteenPercentDiscount";
    public static BigDecimal DiscountValue = BigDecimal.valueOf(0.85);

    @Override
    public boolean shouldApply(Receipt receipt) {
        int cnt = 0;
        for (ReceiptEntry element: receipt.entries()) {
            if (element.product().type() == Product.Type.GRAINS) {
                cnt += element.quantity();
            }
        }
        return cnt >= 3;
    }

    @Override
    protected BigDecimal getDiscountValue() {
        return DiscountValue;
    }

    @Override
    protected String getName() {
        return NAME;
    }
}

package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount extends AbstractDiscount {
    public static String NAME = "TenPercentDiscount";
    public static BigDecimal DiscountValue = BigDecimal.valueOf(0.9);

    @Override
    public boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) > 0;
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

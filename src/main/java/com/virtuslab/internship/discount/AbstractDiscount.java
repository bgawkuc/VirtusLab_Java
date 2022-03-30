package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public abstract class AbstractDiscount implements IDiscount {

    protected boolean shouldApply(Receipt receipt) {
        return false;
    }

    protected abstract BigDecimal getDiscountValue();

    protected abstract String getName();

    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(this.getDiscountValue());
            var discounts = receipt.discounts();
            discounts.add(this.getName());
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }
}

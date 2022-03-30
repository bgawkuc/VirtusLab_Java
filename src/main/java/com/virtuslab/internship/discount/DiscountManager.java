package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class DiscountManager{

    public static Receipt applyDiscounts(Receipt receipt) {
        List<IDiscount> IDiscounts = new ArrayList<>(List.of(new FifteenPercentDiscount(),
                new TenPercentDiscount()));
        for(IDiscount IDiscount : IDiscounts) {
            receipt = IDiscount.apply(receipt);
        }

        return receipt;
    }

//    created for first part of task when 15% discount didn't exist
    public static Receipt apply10PercentDiscount(Receipt receipt) {
        List<IDiscount> IDiscounts = new ArrayList<>(List.of(
                new TenPercentDiscount()));
        for(IDiscount IDiscount : IDiscounts) {
            receipt = IDiscount.apply(receipt);
        }

        return receipt;
    }

}

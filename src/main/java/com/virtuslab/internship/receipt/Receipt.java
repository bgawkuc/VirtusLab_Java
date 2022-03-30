package com.virtuslab.internship.receipt;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries, List<String> discountsList) {
        this(entries, discountsList,
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}

package com.ilearn.book_buddy.rest;

import com.ilearn.book_buddy.data.entity.PurchaseHistory;
import com.ilearn.book_buddy.data.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseHistoryDto {
    private String paymentReference;
    private BigDecimal amountPaid;
    private Date paymentDate;
    private PaymentMethod paymentMethod;
    private String accountNumber;
    private String bankName;

    public static PurchaseHistoryDto map(PurchaseHistory purchaseHistory)  {
        return PurchaseHistoryDto.builder()
                .accountNumber(purchaseHistory.getAccountNumber())
                .paymentReference(purchaseHistory.getReference())
                .amountPaid(purchaseHistory.getAmountPaid())
                .paymentDate(purchaseHistory.getPaymentDate())
                .accountNumber(purchaseHistory.getAccountNumber())
                .bankName(purchaseHistory.getBankName())
                .paymentMethod(purchaseHistory.getPaymentMethod())
                .build();
    }
}

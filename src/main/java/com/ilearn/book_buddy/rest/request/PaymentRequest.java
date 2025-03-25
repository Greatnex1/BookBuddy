package com.ilearn.book_buddy.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String transactionPin;
//    @ValidPaymentMethod
    private String paymentMethod;
    private String cartReference;
    private String accountNumber;
    private String bankName;
    private String userReference;
    private BigDecimal accountBalance;
    private String phoneNumber;
}

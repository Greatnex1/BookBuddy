package com.ilearn.book_buddy.rest.response;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String cartReference;
    private String accountNumber;
    private BigDecimal bankBalance;
    private String bankName;
    private String paymentReference;
    private BigDecimal totalPrice;
    private String phoneNumber;
}

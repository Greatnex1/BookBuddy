package com.ilearn.book_buddy.mapper;

import com.ilearn.book_buddy.rest.request.PaymentRequest;
import com.ilearn.book_buddy.rest.response.PaymentResponse;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentMapper {

    public static PaymentResponse map(PaymentRequest paymentRequest, BigDecimal total, String paymentReference){
        return PaymentResponse.builder()
                .cartReference(paymentRequest.getCartReference())
                .accountNumber(paymentRequest.getAccountNumber())
                .bankName(paymentRequest.getBankName())
                .phoneNumber(paymentRequest.getPhoneNumber())
                .bankBalance(paymentRequest.getAccountBalance().subtract(total))
                .totalPrice(total)
                .paymentReference(paymentReference)
                .build();
    }
}


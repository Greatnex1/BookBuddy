package com.ilearn.book_buddy.rest.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddToCartResponse {
    private String title;
    private String bookReference;
    private String cartReference;
    private int quantity;
    private BigDecimal unitPrice;
}

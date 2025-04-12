package com.ilearn.book_buddy.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddToCartRequest {
    private String userReference;
    private String bookReference;
    private int quantity;
}

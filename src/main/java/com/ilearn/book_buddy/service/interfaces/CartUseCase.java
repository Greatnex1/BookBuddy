package com.ilearn.book_buddy.service.interfaces;

//import com.ilearn.book_buddy.rest.CartItemDto;
import com.ilearn.book_buddy.rest.request.AddToCartRequest;
import com.ilearn.book_buddy.rest.response.AddToCartResponse;

import java.util.List;

public interface CartUseCase {

    AddToCartResponse addToCart(AddToCartRequest addToCartRequest);
//    List<CartItemDto> getCartItems(String cartReference);
    void removeFromCart(String cartReference,String isbn);

}

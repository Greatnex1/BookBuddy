package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.rest.PurchaseHistoryDto;
import com.ilearn.book_buddy.rest.request.PaymentRequest;
import com.ilearn.book_buddy.rest.response.PaymentResponse;

import java.util.List;

public interface PaymentUseCase {
    List<PurchaseHistoryDto> viewPurchaseHistory(String userReference);
    PaymentResponse checkOutViaUssd(PaymentRequest paymentRequest);
    PaymentResponse checkOutViaWeb(PaymentRequest paymentRequest);
    PaymentResponse checkOutViaTransfer(PaymentRequest paymentRequest);
}

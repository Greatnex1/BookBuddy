package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.rest.request.AuthenticationRequest;
import com.ilearn.book_buddy.rest.request.RegisterRequest;
import com.ilearn.book_buddy.rest.response.AuthenticationResponse;

public interface AuthUseCase {

    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

}

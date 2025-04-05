package com.ilearn.book_buddy.rest.request;

import com.ilearn.book_buddy.constants.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "email is required")
    @Email(message = ErrorMessages.INVALID_EMAIL)
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}

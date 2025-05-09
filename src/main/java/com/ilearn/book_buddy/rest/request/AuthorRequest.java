package com.ilearn.book_buddy.rest.request;

import com.ilearn.book_buddy.constants.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    @NotEmpty
    private String fullName;
    @Email(message = ErrorMessages.INVALID_EMAIL)
    private String email;
    @NotEmpty
    private String bio;
}

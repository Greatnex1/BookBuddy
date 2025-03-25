package com.ilearn.book_buddy.rest.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponse {
    private String id;
    private String fullName;
    @Email
    private String email;
    @NotEmpty
    private String bio;
}

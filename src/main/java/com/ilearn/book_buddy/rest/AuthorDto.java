package com.ilearn.book_buddy.rest;

import com.ilearn.book_buddy.data.entity.Author;
import com.ilearn.book_buddy.validator.InputValidator;
import lombok.*;
import org.springframework.beans.BeanUtils;

import static com.ilearn.book_buddy.validator.InputValidator.validateInput;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private String fullName;
    private String email;
    private String bio;
    private String reference;


    public static AuthorDto fromModel(Author author){
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author,authorDto);
        return authorDto;
    }

    public static AuthorDto toDto(Object o) {
        return fromModel((Author) o);
    }


    public void validateAuthorDtoInput(){
        validateInput(getBio());
        validateInput(getEmail());
        validateInput(getReference());
        validateInput(getFullName());
    }
}

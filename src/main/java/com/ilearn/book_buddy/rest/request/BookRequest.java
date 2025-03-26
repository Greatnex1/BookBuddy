package com.ilearn.book_buddy.rest.request;

import com.ilearn.book_buddy.constants.ErrorMessages;
import com.ilearn.book_buddy.data.enums.BookType;
import com.ilearn.book_buddy.validator.annotation.ValidGenre;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private String isbn;
    @NotNull(message = ErrorMessages.PRICE_CANT_BE_NULL)
    @NotBlank
    @Min(value = 1800, message = "Publication year can not be earlier than 1800")
    private int publicationYear;
    @DecimalMin(value = "1.00", inclusive = true, message = "Price cannot be less than 1")
    private BigDecimal price;
    @NotNull(message = "Book type must not be null")
    private BookType bookType;
    @Positive
    private int quantity;
    private String dateUploaded;
    @NotBlank
    private String authorList;
    private MultipartFile file;
     @ValidGenre
     private String genre;

}

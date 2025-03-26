package com.ilearn.book_buddy.rest.request;

import com.ilearn.book_buddy.constants.ErrorMessages;
import com.ilearn.book_buddy.data.enums.BookType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRequest {
    @NotEmpty
    private String title;
    @NotBlank
    private String publishedDate;
    @NotEmpty
    private String description;
    @Positive
    private int isbn;
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
//    private MultipartFile file;
//    @ValidGenre
    private String genre;

}

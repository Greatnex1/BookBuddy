package com.ilearn.book_buddy.data.entity;

import com.ilearn.book_buddy.data.enums.BookType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="books")
public class BookEntity extends AppendableReferenceEntity {
    @Column(nullable = false, length = 30)
    private String name;
   @Column(nullable = false, unique = true)
    @Min(value = 1, message = "Please enter an isbn")
    private int isbn;
    @Column(nullable = false)
    private LocalDate publishDate;
   @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private BookType bookType;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String createdBy;


}

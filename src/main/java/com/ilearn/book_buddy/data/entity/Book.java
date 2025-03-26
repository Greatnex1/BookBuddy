package com.ilearn.book_buddy.data.entity;

import com.ilearn.book_buddy.data.enums.BookType;
import com.ilearn.book_buddy.data.enums.Genre;
import com.ilearn.book_buddy.validator.annotation.ValidGenre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="books")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book extends AppendableReferenceEntity {
    @Column(nullable = false, length = 30)
    private String title;
   @Column(nullable = false, unique = true)
    private String  isbn;
    @Column(nullable = false)
    private int publicationYear;
   @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private BookType bookType;

    private int quantity;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateCreated;
    @Column(nullable = false)
    private LocalDate dateUploaded;
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    private String fileName;

    @ValidGenre
    private Genre genre;

    @UpdateTimestamp
    private Date dateUpdated;



}

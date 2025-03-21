package com.ilearn.book_buddy.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author extends AppendableReferenceEntity {
    @Column(nullable = false, length = 40)
    private String fullName;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String bio;
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateCreated;
    @UpdateTimestamp
    private Date dateUpdated;
}

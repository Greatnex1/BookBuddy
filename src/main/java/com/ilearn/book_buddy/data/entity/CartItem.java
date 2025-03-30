package com.ilearn.book_buddy.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends AppendableReferenceEntity{
    @Column(nullable = false, length = 50)
    private String title;

    private int isbn;

    private String bookId;
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateAdded;

}

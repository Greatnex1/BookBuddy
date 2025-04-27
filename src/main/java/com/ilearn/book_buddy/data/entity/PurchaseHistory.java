package com.ilearn.book_buddy.data.entity;

import com.ilearn.book_buddy.data.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="purchase_histories")
public class PurchaseHistory extends AppendableReferenceEntity {
    private long cartId;
    private long userId;
    private BigDecimal amountPaid;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String accountNumber;
    private String bankName;

}

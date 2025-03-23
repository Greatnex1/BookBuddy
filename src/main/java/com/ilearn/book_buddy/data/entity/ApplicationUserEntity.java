package com.ilearn.book_buddy.data.entity;

import com.ilearn.book_buddy.data.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_entities")
public class ApplicationUserEntity extends AppendableReferenceEntity{

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Gender gender;

    private boolean emailVerified;

    private boolean enabled;
}

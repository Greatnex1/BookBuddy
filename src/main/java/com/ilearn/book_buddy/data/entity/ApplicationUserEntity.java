package com.ilearn.book_buddy.data.entity;

import com.ilearn.book_buddy.data.enums.Gender;

public class ApplicationUserEntity {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Gender gender;

    private boolean emailVerified;

    private boolean enabled;
}

package com.ilearn.book_buddy.repository;

import com.ilearn.book_buddy.data.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, String> {
    Optional<ApplicationUserEntity> findByEmail(String email);

}

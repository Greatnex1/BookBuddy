package com.ilearn.book_buddy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookBuddyApplication.class, args);
	log.info("====Book Buddy Application Started=====");
	}

}

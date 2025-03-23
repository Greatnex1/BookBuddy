package com.ilearn.book_buddy.controller;

import com.ilearn.book_buddy.constants.UrlConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ilearn.book_buddy.constants.UrlConstants.URL_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANT )
public class BookController {

}

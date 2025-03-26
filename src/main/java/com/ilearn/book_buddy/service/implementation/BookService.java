package com.ilearn.book_buddy.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilearn.book_buddy.data.entity.Author;
import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.repository.BookRepository;
import com.ilearn.book_buddy.rest.AuthorDto;
import com.ilearn.book_buddy.rest.BookDto;
import com.ilearn.book_buddy.rest.PageDto;
import com.ilearn.book_buddy.rest.request.AuthorRequest;
import com.ilearn.book_buddy.rest.request.BookRequest;
import com.ilearn.book_buddy.rest.response.AmazonResponse;
import com.ilearn.book_buddy.rest.response.PagedResponse;
import com.ilearn.book_buddy.service.interfaces.AuthorUseCase;
import com.ilearn.book_buddy.service.interfaces.BookUseCase;
import com.ilearn.book_buddy.specifications.BookSpecs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.ilearn.book_buddy.constants.ErrorMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService implements BookUseCase {

    private final BookRepository bookRepository;
    private final AuthorUseCase authorService;
    //    private final StorageService storageService;
//    private final String userDocumentBucketName;
    private final ObjectMapper objectMapper;

//    public BookServiceImpl(BookRepository bookRepository,
//                           AuthorService authorService, @Qualifier(AwsS3Service.NAME) StorageService storageService,
//                           @Value("${SERVICE_BUCKET_NAME}")String userDocumentBucketName, ObjectMapper objectMapper) {
//        this.bookRepository = bookRepository;
//        this.authorService = authorService;
//        this.storageService = storageService;
//        this.userDocumentBucketName = userDocumentBucketName;
//        this.objectMapper = objectMapper;
//    }

    @Override
    @Transactional
    public BookDto createBook(BookRequest bookRequest) throws IOException {
        Optional<Book> foundBook = bookRepository.findBookByIsbn(bookRequest.getIsbn());
        if (foundBook.isPresent()) {
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
        validate(bookRequest);
        //Check if authors already exist, else create author
        List<Author> authors = new ArrayList<>();
        if (!bookRequest.getAuthorList().isEmpty()) {
            List<AuthorDto> authorDtos = objectMapper.readValue(bookRequest.getAuthorList(), new TypeReference<>() {
            });
            populateAuthors(authorDtos, authors);
        }
        Book book = new Book();
        AmazonResponse amazonResponse = null;
        if (bookRequest.getFile() != null) {
//   amazonResponse = uploadedBookUrl(bookRequest.getFile(), bookRequest.getFile().getOriginalFilename());
        }
        BeanUtils.copyProperties(bookRequest, book);
        book.setAuthors(authors);
        book.setUrl(amazonResponse != null ? amazonResponse.getUrl() : "");
        book.setFileName(amazonResponse != null ? amazonResponse.getFileName() : "");
        book.setDateUploaded(LocalDate.parse(bookRequest.getDateUploaded()));
        bookRepository.save(book);
        authors.forEach(author -> {
            author.getBooks().add(book);
            authorService.save(author);
        });
        return BookDto.toDto(book);
    }

    private void validate(BookRequest bookRequest) {
        if (!bookRequest.getGenre().matches("^(FICTION|THRILLER|MYSTERY|POETRY|HORROR|SATIRE)$")) {
            throw new GenericException(INVALID_GENRE, HttpStatus.BAD_REQUEST);
        }
        if (!bookRequest.getIsbn().matches("^[\\d-]+$")) {
            throw new GenericException(INVALID_ISBN, HttpStatus.BAD_REQUEST);
        }
        if (!bookRequest.getTitle().matches("^[a-zA-Z0-9 ]+$")) {
            throw new GenericException(INVALID_TITLE, HttpStatus.BAD_REQUEST);
        }
    }

    private void populateAuthors(List<AuthorDto> authorDtos, List<Author> authors) {
        authorDtos.forEach(authorDto -> {
            AuthorRequest authorRequest = new AuthorRequest();
            Optional<Author> optionalAuthor = authorService.findByEmail(authorDto.getEmail());
            if (optionalAuthor.isEmpty()) {
                BeanUtils.copyProperties(authorDto, authorRequest);
                try {
                    Author savedAuthor = authorService.createAuthor(authorRequest);
                    authors.add(savedAuthor);
                } catch (GenericException e) {
                    throw new RuntimeException(e);
                }
            } else {
                authors.add(optionalAuthor.get());
            }
        });
    }


    @Override
    public void updateBook(String id, BookRequest bookRequest) throws IOException {
        Book book = this.findBook(id);
        List<Author> authors = new ArrayList<>();
//        AmazonResponse amazonResponse = null;
//        if (bookRequest.getFile() != null){
//            storageService.deleteFile(book.getFileName());
//            System.out.println(book.getFileName());
//            amazonResponse = uploadedBookUrl(bookRequest.getFile(), book.getFileName());
//        }
//        if(!bookRequest.getAuthorList().isEmpty()) {
//            List<AuthorDto> authorDtos = objectMapper.readValue(bookRequest.getAuthorList(), new TypeReference<>() {
//            });
//            populateAuthors(authorDtos, authors);
//        }
//        BeanUtils.copyProperties(bookRequest, book);
//        book.setUrl(amazonResponse != null ? amazonResponse.getUrl() : null);
//        book.setAuthors(authors);
//        book.setFileName(amazonResponse != null ? amazonResponse.getFileName() : null);
        bookRepository.save(book);
    }

    @Override
    public PagedResponse findAll(int pageNumber, int noOfItems) {
        Pageable pageable = PageRequest.of(pageNumber, noOfItems, Sort.by("title"));
        Page<Book> page = bookRepository.findAll(pageable);
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("totalNumberOfPages", page.getTotalPages());
        pageResult.put("totalNumberOfElementsInDatabase", page.getTotalElements());
        if (page.hasNext()) {
            pageResult.put("nextPage", page.nextPageable());
        }
        List<Book> books = page.getContent();
        List<BookDto> bookDtoList = mapBookToBookDto(books);
        if (page.hasPrevious()) {
            pageResult.put("previousPage", page.previousPageable());
        }
        pageResult.put("books", bookDtoList);
        pageResult.put("NumberOfElementsInPage", page.getNumberOfElements());
        pageResult.put("pageNumber", page.getNumber());
        pageResult.put("size", page.getSize());

        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setPagedResponse(pageResult);
        return pagedResponse;

    }

    private static List<BookDto> mapBookToBookDto(List<Book> books) {
        return books.stream().map(book -> {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            List<AuthorDto> authorDtoList = mapAuthorToAuthorDto(book);
            bookDto.setAuthors(authorDtoList);
            return bookDto;
        }).toList();
    }

    private static List<AuthorDto> mapAuthorToAuthorDto(Book book) {
        List<Author> authors = book.getAuthors();
        return authors.stream().map(author -> {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            return authorDto;
        }).toList();
    }

    @Override
    public BookDto findById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
        return BookDto.toDto(book);
    }

    @Override
    public PageDto searchCriteria(String query, int page, int size) {
        BookSpecs bookSpecs = new BookSpecs(query);
        bookSpecs.setPage(page);
        bookSpecs.setSize(size);
        Page<Book> bookPage = bookRepository.findAll(bookSpecs, bookSpecs.getPageable());
        return PageDto.build(bookPage, BookDto::toDto);
    }

    @Override
    public void deleteBook(String id) {
        Book book = this.findBook(id);
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> filterByAuthor(String reference) {
//     Long authorId= AppendableReferenceUtils.getIdFrom(reference);
        Optional<Author> authorOptional = authorService.findById(reference);
        if (authorOptional.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> books = authorOptional.get().getBooks();
        return mapBookToBookDto(books);
    }

    //
//    private AmazonResponse uploadedBookUrl(MultipartFile multipartFile, String fileName) throws IOException {
//        UploadObject uploadObject = new UploadObject();
//        uploadObject.setBucketName(userDocumentBucketName);
//        uploadObject.setName(fileName);
//        log.info("Event={}, uploadObject={}", "UPLOAD BOOK", uploadObject);
//        return storageService.uploadToBucket(multipartFile.getInputStream(), uploadObject);
//}
    private Book findBook(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}


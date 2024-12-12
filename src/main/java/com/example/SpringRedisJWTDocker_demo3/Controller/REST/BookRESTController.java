package com.example.SpringRedisJWTDocker_demo3.Controller.REST;

import com.example.SpringRedisJWTDocker_demo3.Model.Book;
import com.example.SpringRedisJWTDocker_demo3.Model.Book_Author;
import com.example.SpringRedisJWTDocker_demo3.Model.Review;
import com.example.SpringRedisJWTDocker_demo3.Service.BookService;
import com.example.SpringRedisJWTDocker_demo3.Service.Book_AuthorService;
import com.example.SpringRedisJWTDocker_demo3.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/api")
public class BookRESTController {
    //--------------
    @Autowired
    private BookService operations;
    @Autowired
    private Book_AuthorService operationsAuthors;
    @Autowired
    private ReviewService operationsReview;
    //--------------

    @GetMapping("/all")
    public List<Book> allBooks(){
        return operations.getAllBooks();
    }
    @GetMapping("/concrete/{id}")
    public Book getConcreteBook(@PathVariable("id")int id){
        return operations.getConcreteBook(id);
    }
    @PostMapping("/save_book")
    public ResponseEntity<HttpStatus> saveBook(@RequestBody Book book){
        operations.saveBook(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id")int id){
        operations.deleteBook(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Для MANY TO MANY & ONE TO MANY
    @PutMapping("/add_author/{book_id}/{author_id}")
    public ResponseEntity<HttpStatus> addAuthorToBook(@PathVariable("book_id") int book_id,@PathVariable("author_id")int author_id){
        Book_Author author=operationsAuthors.getConcreteAuthor(author_id);
        Book book=operations.getConcreteBook(book_id);

        author.getBooks().add(book);
        book.getAuthors().add(author);

        operations.saveBook(book);
        operationsAuthors.saveAuthor(author);

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/add_review/{book_id}/{review_id}")
    public ResponseEntity<HttpStatus> addReviewToBooj(@PathVariable("book_id")int book_id,@PathVariable("review_id")int review_id){
        Review review=operationsReview.getConcreteReview(review_id);
        Book book=operations.getConcreteBook(book_id);

        review.setBook(book);

        operationsReview.saveReview(review);
        operations.saveBook(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    //Дополнительные методы
    @GetMapping("/authors/less-than/{count}")
    public List<Book> getBooksWithAuthorsLessThan(@PathVariable int count) {
        return operations.findBooksWithAuthorsLessThan(count);
    }

    @GetMapping("/authors/greater-than/{count}")
    public List<Book> getBooksWithAuthorsGreaterThan(@PathVariable int count) {
        return operations.findBooksWithAuthorsGreaterThan(count);
    }

    @GetMapping("/author/{firstName}")
    public List<Book> getBooksByAuthorFirstName(@PathVariable String firstName) {
        return operations.findBooksByAuthorFirstName(firstName);
    }

    @GetMapping("/publication-year/{year}")
    public List<Book> getBooksByPublicationYear(@PathVariable int year) {
        return operations.findBooksByPublicationYear(year);
    }

    @GetMapping("/rating/greater-than/{rating}")
    public List<Book> getBooksWithRatingGreaterThan(@PathVariable double rating) {
        return operations.findBooksWithRatingGreaterThan(rating);
    }
}

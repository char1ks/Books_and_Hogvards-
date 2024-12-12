package com.example.SpringRedisJWTDocker_demo3.Service;

import com.example.SpringRedisJWTDocker_demo3.Model.Book;
import com.example.SpringRedisJWTDocker_demo3.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Cacheable(value = "book", key = "#id")
    public Book getConcreteBook(int id){
        return bookRepository.findById(id).orElse(null);
    }
    @CachePut(value = "book", key = "#result.id")
    public Book saveBook(Book book){
         return bookRepository.save(book);
    }
    @CacheEvict(value = "book", key = "#id")
    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }
    // Получить книги, где количество авторов меньше указанного
    public List<Book> findBooksWithAuthorsLessThan(int authorCount) {
        return bookRepository.findBooksWithAuthorsLessThan(authorCount);
    }

    // Получить книги, где количество авторов больше указанного
    public List<Book> findBooksWithAuthorsGreaterThan(int authorCount) {
        return bookRepository.findBooksWithAuthorsGreaterThan(authorCount);
    }

    // Получить книги, написанные конкретным автором по его имени
    public List<Book> findBooksByAuthorFirstName(String firstName) {
        return bookRepository.findBooksByAuthorFirstName(firstName);
    }

    // Получить книги, опубликованные в определенном году
    public List<Book> findBooksByPublicationYear(int year) {
        return bookRepository.findBooksByPublicationYear(year);
    }

    // Получить книги, у которых рейтинг выше указанного
    public List<Book> findBooksWithRatingGreaterThan(double rating) {
        return bookRepository.findBooksWithRatingGreaterThan(rating);
    }
}

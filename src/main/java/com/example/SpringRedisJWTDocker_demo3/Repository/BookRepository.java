package com.example.SpringRedisJWTDocker_demo3.Repository;

import com.example.SpringRedisJWTDocker_demo3.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Получить книги, где количество авторов меньше указанного
    @Query("SELECT b FROM Book b JOIN b.authors a GROUP BY b.id HAVING COUNT(a) < :authorCount")
    List<Book> findBooksWithAuthorsLessThan(@Param("authorCount") int authorCount);

    // Получить книги, где количество авторов больше указанного
    @Query("SELECT b FROM Book b JOIN b.authors a GROUP BY b.id HAVING COUNT(a) > :authorCount")
    List<Book> findBooksWithAuthorsGreaterThan(@Param("authorCount") int authorCount);

    // Получить книги, написанные конкретным автором по его имени
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.firstName = :firstName")
    List<Book> findBooksByAuthorFirstName(@Param("firstName") String firstName);

    // Получить книги, опубликованные в определенном году
    @Query("SELECT b FROM Book b WHERE b.publicationDate >= :startDate AND b.publicationDate < :endDate")
    List<Book> findBooksByPublicationYear(@Param("year") int year);

    // Получить книги, у которых рейтинг выше указанного
    @Query("SELECT b FROM Book b WHERE b.rating > :rating")
    List<Book> findBooksWithRatingGreaterThan(@Param("rating") double rating);
}
package com.example.SpringRedisJWTDocker_demo3.Repository;

import com.example.SpringRedisJWTDocker_demo3.Model.Book_Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book_AuthorRepository extends JpaRepository<Book_Author,Integer> {

    Book_Author findByUsername(String username);
}

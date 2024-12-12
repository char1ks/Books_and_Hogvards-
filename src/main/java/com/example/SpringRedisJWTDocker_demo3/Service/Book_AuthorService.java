package com.example.SpringRedisJWTDocker_demo3.Service;

import com.example.SpringRedisJWTDocker_demo3.Model.Book_Author;
import com.example.SpringRedisJWTDocker_demo3.Redis.RedisPublisher;
import com.example.SpringRedisJWTDocker_demo3.Repository.Book_AuthorRepository;
import com.example.SpringRedisJWTDocker_demo3.Security.Book_AuthorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class Book_AuthorService implements UserDetailsService {

    @Autowired
    private Book_AuthorRepository operations;

    @Autowired
    private RedisPublisher publisher;

    public List<Book_Author> getAllAuthors(){
        return operations.findAll();
    }

    @Cacheable(value = "author", key = "#id")
    public Book_Author getConcreteAuthor(int id) {
        publisher.publish("Был получен чел с ID:"+id);
        return operations.findById(id).orElse(null);
    }
    @CachePut(value = "author", key = "#author.id")
    public void saveAuthor(Book_Author author){
        operations.save(author);
        publisher.publish("Был сохранен чел с ID:"+author.getId());

    }
    @CacheEvict(value = "heroes", key = "#id")
    public  void deleteAuthor(int id){
        operations.deleteById(id);
        publisher.publish("Был удален чел с ID:"+id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Book_Author> findAuthor = Optional.ofNullable(operations.findByUsername(username));
        if(findAuthor.isEmpty())
            throw new UsernameNotFoundException("Автор не найден");
        return new Book_AuthorDetails(findAuthor.get());
    }
}

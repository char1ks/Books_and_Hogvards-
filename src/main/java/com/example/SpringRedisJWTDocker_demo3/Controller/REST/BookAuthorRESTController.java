package com.example.SpringRedisJWTDocker_demo3.Controller.REST;

import com.example.SpringRedisJWTDocker_demo3.Model.Book_Author;
import com.example.SpringRedisJWTDocker_demo3.Security.Book_AuthorDetails;
import com.example.SpringRedisJWTDocker_demo3.Security.JWTUtil;
import com.example.SpringRedisJWTDocker_demo3.Service.Book_AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book_author/api")
public class BookAuthorRESTController {

    @Autowired
    private Book_AuthorService operations;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/all")
    @PreAuthorize("hasRole('GOLD')")
    public List<Book_Author> getAllAuthors(){
        return operations.getAllAuthors();
    }

    @GetMapping("/current")
    public Book_Author getCurrentAuthor(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Book_AuthorDetails details= (Book_AuthorDetails) authentication.getPrincipal();
        return details.getBookAuthor();
    }

    @GetMapping("/concrete/{id}")
    public Book_Author getConcreteAuthor(@PathVariable("id")int id){
        return operations.getConcreteAuthor(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginClient(@RequestBody Book_Author author) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(author.getUsername(), author.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful for user: " + author.getUsername());
        } catch (Exception exc) {
            System.out.println("Authentication failed: " + exc.getMessage());
            throw new RuntimeException(exc);
        }
        String token = jwtUtil.generateToken(author.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(Map.of("Token", token), headers, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> regClient(@RequestBody Book_Author author) {
        System.out.println("Регистрация пользователя: " + author.getUsername());
        operations.saveAuthor(author);
        String token = jwtUtil.generateToken(author.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(Map.of("Token", token), headers, HttpStatus.OK);
    }

}

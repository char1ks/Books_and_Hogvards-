package com.example.SpringRedisJWTDocker_demo3.Controller.REST;

import com.example.SpringRedisJWTDocker_demo3.Model.Review;
import com.example.SpringRedisJWTDocker_demo3.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/api")
public class ReviewRESTController {

    @Autowired
    private ReviewService operations;

    @GetMapping("/all")
    public List<Review> getAllReviews(){
        return operations.getAllReviews();
    }
    @GetMapping("/concrete/{id}")
    public Review getConcreteReview(@PathVariable("id")int id){
        return operations.getConcreteReview(id);
    }
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveReview(@RequestBody Review review){
        operations.saveReview(review);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id")int id){
        operations.deleteReview(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

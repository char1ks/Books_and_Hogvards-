package com.example.SpringRedisJWTDocker_demo3.Service;

import com.example.SpringRedisJWTDocker_demo3.Model.Review;
import com.example.SpringRedisJWTDocker_demo3.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository operations;

    public List<Review> getAllReviews(){
        return operations.findAll();
    }

    @Cacheable(value = "review", key = "#id")
    public Review getConcreteReview(int id){
        return operations.findById(id).orElse(null);
    }
    @CachePut(value = "review", key = "#result.id")
    public Review saveReview(Review review){
        return operations.save(review);
    }
    @CacheEvict(value = "review", key = "#id")
    public void deleteReview(int id){
        operations.deleteById(id);
    }
}

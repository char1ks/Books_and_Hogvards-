package com.example.SpringRedisJWTDocker_demo3.Repository;

import com.example.SpringRedisJWTDocker_demo3.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
}

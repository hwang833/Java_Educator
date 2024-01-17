package com.ztcollins.JavaEducator.repository;

import com.ztcollins.JavaEducator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * I haven't gotten this far in the video for this to be explained yet.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    public boolean existsByEmail(String email);
    public User findByEmail(String email);

    //public boolean existsByUserName(String userName);

    //public boolean groupByScore(int score);

    //public boolean findByScoreGreaterThan(int score);

}

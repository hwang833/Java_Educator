package com.ztcollins.JavaEducator.repository;

import com.ztcollins.JavaEducator.model.Maze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Maze entities.
 * This interface extends JpaRepository, providing standard methods for CRUD operations,
 * and includes a custom method to find a maze by its difficulty level.
 */

public interface MazeRepository extends JpaRepository<Maze, Long> {
    /**
     * This method attempts to find a maze by its difficulty level.
     * This method queries the database for a maze entity that matches the given difficulty.
     * 
     * @param difficulty The difficulty level of the maze to find
     * @return The Maze entity matching the specified difficulty or null if no such maze exists.
     */
    @Query("SELECT s FROM Maze s WHERE s.difficulty =:difficulty")
    Maze findByDifficulty(@Param("difficulty") String difficulty);

    //Maze findById(String id);
}

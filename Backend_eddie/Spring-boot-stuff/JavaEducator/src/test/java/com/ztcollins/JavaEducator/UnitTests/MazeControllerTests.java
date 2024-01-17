package com.ztcollins.JavaEducator.UnitTests;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ztcollins.JavaEducator.controller.MazeController;

/**
 * Test class for MazeController. 
 * Uses MockMvc to simulate HTTP requests and assert the responses.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MazeController.class)
class MazeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests the getMaze endpoint for an 'easy' difficulty.
     * Ensures that the endpoint returns a status of OK and the content specific to an easy maze.
     * 
     * @throws Exception if the perform method of MockMvc fails
     */
    @Test
    void getEasyMaze() throws Exception {
        mockMvc.perform(get("/maze/easy"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("O"))); // Check for content specific to the easy maze
    }

    /**
     * Tests the getMaze endpoint for a 'hard' difficulty.
     * Ensures that the endpoint returns a status of OK and the content specific to a hard maze.
     * 
     * @throws Exception if the perform method of MockMvc fails
     */
    @Test
    void getHardMaze() throws Exception {
        mockMvc.perform(get("/maze/hard"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("W"))); // Check for content specific to the hard maze
    }

    /**
     * Tests the getMaze endpoint for an unknown difficulty.
     * Ensures that the endpoint returns a status of OK and the content for the default maze.
     * 
     * @throws Exception if the perform method of MockMvc fails
     */
    @Test
    void getDefaultMazeForUnknownDifficulty() throws Exception {
        mockMvc.perform(get("/maze/unknown"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Default"))); // Assuming your default maze has some unique identifiable content
    }
}


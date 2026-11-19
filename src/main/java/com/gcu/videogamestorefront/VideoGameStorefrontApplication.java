package com.gcu.videogamestorefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Video Game Storefront.
 * Starts the Spring Boot application and initializes
 * the application context.
 */
@SpringBootApplication
public class VideoGameStorefrontApplication {

    /**
     * Main entry point for the Video Game Storefront application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(VideoGameStorefrontApplication.class, args);
    }
}

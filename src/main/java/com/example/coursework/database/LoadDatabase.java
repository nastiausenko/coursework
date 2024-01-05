package com.example.coursework.database;

import com.example.coursework.api.model.Quiz;
import com.example.coursework.repos.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(QuizRepository quizRepository) {

    return args -> {
      log.info("Preloading {}", quizRepository.save(new Quiz(2, "Frodo Bagginess", "thief")));
      log.info("Preloading {}", quizRepository.save(new Quiz(1, "hello", "start")));
    };
  }
}

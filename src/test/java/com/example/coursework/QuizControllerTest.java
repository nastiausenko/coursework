package com.example.coursework;

import com.example.coursework.api.controller.QuizController;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.exceptions.QuizNotFoundException;
import com.example.coursework.repos.QuizRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizControllerTest {

    @Mock
    private QuizRepository mockRepository;

    @InjectMocks
    private QuizController quizController;

    @Test
    public void testGetAllQuizzes() {
        List<Quiz> mockQuizzes = Arrays.asList(new Quiz(1, "Quiz1", "Description1"),
                new Quiz(2, "Quiz2", "Description2"));
        when(mockRepository.findAll()).thenReturn(mockQuizzes);

        List<Quiz> result = quizController.getAll().join();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetQuizById() {
        Quiz mockQuiz = new Quiz(1, "Quiz1", "Description1");
        when(mockRepository.findById(1)).thenReturn(Optional.of(mockQuiz));

        Quiz result = quizController.getById(1).join();

        assertEquals("Quiz1", result.getName());
    }

    @Test
    public void testPostQuiz() {
        Quiz newQuiz = new Quiz(null, "New Quiz", "New Description");
        when(mockRepository.save(any(Quiz.class))).thenReturn(newQuiz);

        Quiz result = quizController.newQuiz(newQuiz).join();

        assertEquals("New Quiz", result.getName());
    }

    @Test
    public void testPutQuiz() {
        Quiz existingQuiz = new Quiz(1, "Existing Quiz", "Existing Description");
        Quiz newQuiz = new Quiz(1, "Updated Quiz", "Updated Description");

        when(mockRepository.findById(1)).thenReturn(Optional.of(existingQuiz));
        when(mockRepository.save(any(Quiz.class))).thenReturn(newQuiz);

        Quiz result = quizController.replaceQuiz(newQuiz, 1).join();

        assertEquals("Updated Quiz", result.getName());
    }

    @Test
    public void testDeleteQuiz() {
        when(mockRepository.existsById(1)).thenReturn(true);

        CompletableFuture<Void> result = quizController.deleteQuiz(1);

        assertNull(result.join());
        verify(mockRepository, times(1)).deleteById(1);
    }

    @Test(expected = QuizNotFoundException.class)
    public void testDeleteQuizNotFound() {
        when(mockRepository.existsById(1)).thenReturn(false);

        CompletableFuture<Void> result = quizController.deleteQuiz(1);

        result.join();
    }
}

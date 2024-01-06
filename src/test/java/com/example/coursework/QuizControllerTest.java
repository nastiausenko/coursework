package com.example.coursework;

import com.example.coursework.api.controller.QuizController;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.api.service.QuizService;
import com.example.coursework.exceptions.QuizNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizControllerTest {

    @Mock
    private QuizService mockService;

    @InjectMocks
    private QuizController quizController;

    @Test
    public void testGetAllQuizzes() {
        List<Quiz> mockQuizzes = Arrays.asList(new Quiz(1, "Quiz1", "Description1"),
                new Quiz(2, "Quiz2", "Description2"));
        CompletableFuture<List<Quiz>> completedFuture = CompletableFuture.completedFuture(mockQuizzes);
        when(mockService.getAllQuiz()).thenReturn(completedFuture);

        CompletableFuture<List<Quiz>> resultFuture = quizController.getAll();

        List<Quiz> result = resultFuture.join();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetQuizById() {
        Quiz mockQuiz = new Quiz(1, "Quiz1", "Description1");
        CompletableFuture<Quiz> completedFuture = CompletableFuture.completedFuture(mockQuiz);
        when(mockService.getQuizById(1)).thenAnswer(invocation -> completedFuture);

        CompletableFuture<Quiz> resultFuture = quizController.getById(1);

        Quiz result = resultFuture.join();

        assertEquals("Quiz1", result.getName());
    }

    @Test
    public void testPostQuiz() {
        Quiz newQuiz = new Quiz(null, "New Quiz", "New Description");
        CompletableFuture<Quiz> completedFuture = CompletableFuture.completedFuture(newQuiz);
        when(mockService.createQuiz(any(Quiz.class))).thenReturn(completedFuture);

        CompletableFuture<Quiz> resultFuture = quizController.newQuiz(newQuiz);
        Quiz result = resultFuture.join();

        assertEquals("New Quiz", result.getName());
    }

    @Test
    public void testPutQuiz() throws ExecutionException, InterruptedException {
        Quiz newQuiz = new Quiz(1, "Updated Quiz", "Updated Description");

        CompletableFuture<Quiz> future = CompletableFuture.completedFuture(newQuiz);
        when(mockService.updateQuiz(1, newQuiz)).thenReturn(future);

        Quiz result = quizController.replaceQuiz(newQuiz, 1).get();

        assertEquals("Updated Quiz", result.getName());
    }

    @Test
    public void testDeleteQuiz() {
        doAnswer(invocation -> {
            CompletableFuture<Void> future = new CompletableFuture<>();
            future.complete(null);
            return future;
        }).when(mockService).deleteQuiz(1);

        CompletableFuture<Void> result = quizController.deleteQuiz(1);

        assertNull(result.join());
        verify(mockService, times(1)).deleteQuiz(1);
    }

    @Test(expected = QuizNotFoundException.class)
    public void testDeleteQuizNotFound() {
        doThrow(new QuizNotFoundException(1)).when(mockService).deleteQuiz(1);

        CompletableFuture<Void> result = quizController.deleteQuiz(1);

        result.join();
    }}

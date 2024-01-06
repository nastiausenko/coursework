package com.example.coursework;

import com.example.coursework.api.controller.QuestionController;
import com.example.coursework.api.model.Question;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.api.service.QuestionService;
import com.example.coursework.exceptions.QuestionNotFoundException;
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
public class QuestionControllerTest {

    private final Quiz quiz =  new Quiz(1, "Quiz1", "Description1");
    @Mock
    private QuestionService mockService;

    @InjectMocks
    private QuestionController questionController;

    @Test
    public void testGetAllQuestions() {
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "Multiple Choice", 1, "Question1", quiz),
                new Question(2, "Short Answer", 2, "Question2", quiz)
        );

        CompletableFuture<List<Question>> completedFuture = CompletableFuture.completedFuture(mockQuestions);
        when(mockService.getAllQuestions()).thenReturn(completedFuture);

        CompletableFuture<List<Question>> resultFuture = questionController.getAll();

        List<Question> result = resultFuture.join();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetQuestionById() {
        Question mockQuestion = new Question(1, "Multiple Choice", 1, "Question1", quiz);

        CompletableFuture<Question> completedFuture = CompletableFuture.completedFuture(mockQuestion);
        when(mockService.getQuestionById(1)).thenAnswer(invocation -> completedFuture);

        CompletableFuture<Question> resultFuture = questionController.getById(1);

        Question result = resultFuture.join();

        assertEquals("Multiple Choice", result.getType());
    }

    @Test
    public void testPostQuestion() {
        Question newQuestion = new Question(null, "New Type", 3, "New Question", quiz);
        CompletableFuture<Question> completedFuture = CompletableFuture.completedFuture(newQuestion);
        when(mockService.createQuestion(any(Question.class))).thenReturn(completedFuture);

        CompletableFuture<Question> resultFuture = questionController.newQuestion(newQuestion);
        Question result = resultFuture.join();

        assertEquals("New Type", result.getType());
    }

    @Test
    public void testPutQuestion() throws ExecutionException, InterruptedException {
        Question newQuestion = new Question(1, "Updated Type", 1, "Updated Question", quiz);

        CompletableFuture<Question> future = CompletableFuture.completedFuture(newQuestion);
        when(mockService.updateQuestion(1, newQuestion)).thenReturn(future);

        Question result = questionController.replaceQuestion(newQuestion, 1).get();

        assertEquals("Updated Type", result.getType());
    }


    @Test
    public void testDeleteQuestion() {
        doAnswer(invocation -> {
            CompletableFuture<Void> future = new CompletableFuture<>();
            future.complete(null);
            return future;
        }).when(mockService).deleteQuestion(1);

        CompletableFuture<Void> result = questionController.deleteQuestion(1);

        assertNull(result.join());
        verify(mockService, times(1)).deleteQuestion(1);
    }

    @Test(expected = QuestionNotFoundException.class)
    public void testDeleteQuizNotFound() {
        doThrow(new QuestionNotFoundException(1)).when(mockService).deleteQuestion(1);

        CompletableFuture<Void> result = questionController.deleteQuestion(1);

        result.join();
    }
}

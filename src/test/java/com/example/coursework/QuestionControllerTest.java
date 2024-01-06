package com.example.coursework;

import com.example.coursework.api.controller.QuestionController;
import com.example.coursework.api.model.Question;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.exceptions.QuestionNotFoundException;
import com.example.coursework.repos.QuestionRepository;
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
public class QuestionControllerTest {

    private final Quiz quiz =  new Quiz(1, "Quiz1", "Description1");
    @Mock
    private QuestionRepository mockRepository;

    @InjectMocks
    private QuestionController questionController;

    @Test
    public void testGetAllQuestions() {
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "Multiple Choice", 1, "Question1", quiz),
                new Question(2, "Short Answer", 2, "Question2", quiz)
        );
        when(mockRepository.findAll()).thenReturn(mockQuestions);

        List<Question> result = questionController.getAll().join();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetQuestionById() {
        Question mockQuestion = new Question(1, "Multiple Choice", 1, "Question1", quiz);
        when(mockRepository.findById(1)).thenReturn(Optional.of(mockQuestion));

        Question result = questionController.getById(1).join();

        assertEquals("Multiple Choice", result.getType());
    }

    @Test
    public void testNewQuestion() {
        Question newQuestion = new Question(null, "New Type", 3, "New Question", quiz);
        when(mockRepository.save(any(Question.class))).thenReturn(newQuestion);

        Question result = questionController.newQuestion(newQuestion).join();

        assertEquals("New Type", result.getType());
    }

    @Test
    public void testReplaceQuestion() {
        Question existingQuestion = new Question(1, "Existing Type", 1, "Existing Question", quiz);
        Question newQuestion = new Question(1, "Updated Type", 1, "Updated Question", quiz);

        when(mockRepository.findById(1)).thenReturn(Optional.of(existingQuestion));
        when(mockRepository.save(any(Question.class))).thenReturn(newQuestion);

        Question result = questionController.replaceQuestion(newQuestion, 1).join();

        assertEquals("Updated Type", result.getType());
    }

    @Test
    public void testDeleteQuestion() {
        CompletableFuture<Void> result = questionController.deleteQuestion(1);

        assertNull(result.join());
        verify(mockRepository, times(1)).deleteById(1);
    }

    @Test(expected = QuestionNotFoundException.class)
    public void testDeleteQuizNotFound() {
        when(mockRepository.existsById(1)).thenReturn(false);

        CompletableFuture<Void> result = questionController.deleteQuestion(1);

        result.join();
    }
}

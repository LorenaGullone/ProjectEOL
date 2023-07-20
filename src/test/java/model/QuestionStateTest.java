package model;

import static org.junit.Assert.*;

import model.exam.QuestionStateClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionStateTest {
    private QuestionStateClient questionStateClient;

    @Before
    public void setUp() {
        questionStateClient = new QuestionStateClient(1, "Sample question");
    }

    @Test
    public void testGetQuestion() {
        assertEquals("Sample question", questionStateClient.getQuestion());
    }

    @Test
    public void testSetQuestion() {
        questionStateClient.setQuestion("New question");
        assertEquals("New question", questionStateClient.getQuestion());
    }

    @Test
    public void testAddOption() {
        assertTrue(questionStateClient.addOption("Option 1"));
        assertTrue(questionStateClient.addOption("Option 2"));
        assertFalse(questionStateClient.addOption("Option 1")); // Duplicate option
    }

    @Test
    public void testSetActive() {
        assertFalse(questionStateClient.getActive());
        questionStateClient.setActive(true);
        assertTrue(questionStateClient.getActive());
    }

    @Test
    @Order(1)
    public void testStart() {
        assertEquals("NON_ATTIVA", questionStateClient.innerState());
        assertFalse(questionStateClient.getActive());

        questionStateClient.start();
        assertEquals("ATTIVA", questionStateClient.innerState());
        assertTrue(questionStateClient.getActive());
    }

    @Test
    @Order(2)
    public void testSubmit() {
        assertTrue(questionStateClient.getActive());
        questionStateClient.submit();
        assertEquals("PRESENTATA", questionStateClient.innerState());
    }

    @Test
    @Order(3)
    public void testClosed() {
        assertFalse(questionStateClient.getActive());
        assertEquals("CHIUSA", questionStateClient.innerState());
    }

}


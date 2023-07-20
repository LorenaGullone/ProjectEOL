package model;
import static org.junit.Assert.*;

import model.exam.ExamIF;
import model.exam.MultipleChoiceExam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ExamTest {
    private ExamIF abstractExam;

    @Before
    public void setUp() {
        abstractExam = new MultipleChoiceExam(new Date());
        abstractExam.setTTL(60000);
        abstractExam.setID(1);
        abstractExam.setQuestionsNumber(3);
    }

    @Test
    public void testGetID() {
        assertEquals(1, abstractExam.getID());
    }

    @Test
    public void testGetStatus() {
        assertFalse(abstractExam.getStatus());
    }

    @Test
    public void testGetStartDate() {
        assertNotNull(abstractExam.getStartDate());
    }

    @Test
    public void testGetType() {
        assertNull(abstractExam.getType());
        abstractExam.setType("Exam Type");
        assertEquals("Exam Type", abstractExam.getType());
    }

    @Test
    public void testGetTTL() {
        assertEquals(60000, abstractExam.getTTL());
    }

    @Test
    public void testGetQuestionsNumber() {
        assertEquals(3, abstractExam.getQuestionsNumber());
    }


    @Test
    public void testStart() {
        assertFalse(abstractExam.getStatus());
        abstractExam.start();
        assertTrue(abstractExam.getStatus());
    }


}
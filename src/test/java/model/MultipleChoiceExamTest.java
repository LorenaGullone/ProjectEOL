package model;

import static org.junit.Assert.*;

import model.exam.MultipleChoiceExam;
import model.exam.MultipleChoiceExamBuilder;
import model.exam.Question;
import model.exam.builder.ExamParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class MultipleChoiceExamTest {
    private MultipleChoiceExam exam;

    @Before
    public void setUp() {
        Date exam_date= new Date(System.currentTimeMillis());
        exam = new MultipleChoiceExam(exam_date);
        MultipleChoiceExamBuilder builder = new MultipleChoiceExamBuilder(exam);
        ExamParser parser = new ExamParser(builder, "C:\\ProjectEOL\\ProjectEOL\\src\\main\\java\\model\\exam\\builder\\Exam.txt");
        parser.build();
    }

    @Test
    public void testValutaRisposte() {
        // Test con risposte corrette
        ArrayList<Integer> risposteCorrette = new ArrayList<>();
        risposteCorrette.add(0);
        risposteCorrette.add(1);
        risposteCorrette.add(2);
        int punteggioCorrette = exam.valutaRisposte(risposteCorrette);
        assertEquals(9, punteggioCorrette);

        // Test con risposte parzialmente corrette ma comunque date
        ArrayList<Integer> risposteParziali = new ArrayList<>();
        risposteParziali.add(0);
        risposteParziali.add(2);
        risposteParziali.add(1);
        int punteggioParziali = exam.valutaRisposte(risposteParziali);
        assertEquals(3, punteggioParziali);

        // Test con risposte parzialmente corrette di cui 1 non è stata data
        ArrayList<Integer> risposteNonDate = new ArrayList<>();
        risposteParziali.add(0);
        risposteParziali.add(2);
        risposteParziali.add(-1);
        int punteggioUnaNonData = exam.valutaRisposte(risposteParziali);
        assertEquals(2, punteggioUnaNonData);

        // Test con risposte corrette di cui 1 non è stata data
        ArrayList<Integer> risposteNonDate2 = new ArrayList<>();
        risposteParziali.add(0);
        risposteParziali.add(1);
        risposteParziali.add(-1);
        int punteggioUnaNonData2 = exam.valutaRisposte(risposteParziali);
        assertEquals(5, punteggioUnaNonData2);

        // Test con tutte risposte errate
        ArrayList<Integer> risposteErrate = new ArrayList<>();
        risposteErrate.add(1);
        risposteErrate.add(2);
        risposteErrate.add(0);
        int punteggioErrate = exam.valutaRisposte(risposteErrate);
        assertEquals(0, punteggioErrate);

        // Test con tutte risposte non date
        ArrayList<Integer> risposteNulle = new ArrayList<>();
        risposteErrate.add(-1);
        risposteErrate.add(-1);
        risposteErrate.add(-1);
        int punteggioNulle = exam.valutaRisposte(risposteErrate);
        assertEquals(-3, punteggioErrate);
    }

}

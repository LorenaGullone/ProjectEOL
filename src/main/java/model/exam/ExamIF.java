package model.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface ExamIF {
    int getID();
    boolean getStatus();
    long getStartDate();
    int getQuestionsNumber();
    String getType();
    long getTTL();
    Map<Integer, Question> getTest();
    void addExamListener(ExamListener listener);
    void removeExamListener(ExamListener listener);
    void setType(String type);
    void setStartDate(Date start);
    void setQuestionsNumber(int number);
    void setID(int id);
    void setTTL(long time);
    int valutaRisposte(ArrayList<Integer> risposte);
    void start();

}//ExamIF

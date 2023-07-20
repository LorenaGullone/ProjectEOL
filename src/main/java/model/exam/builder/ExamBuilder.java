package model.exam.builder;

public interface ExamBuilder {
    void createExam(String type);
    void closeExam();
    void addQuestion(String question, int number);
    void closeQuestion();
    void addOption(String option, int number);
    void setResponse(String response, int number);

}//ExamBuilder

package model.exam;

import model.exam.builder.ExamBuilder;

public class MultipleChoiceExamBuilder implements ExamBuilder {
    private AbstractExam esame;
    private int counter=0;

    public MultipleChoiceExamBuilder(AbstractExam e){
        this.esame=e;
    }

    //DEFINIZIONE DEI METODI DELL'INTERFACCIA
    @Override
    public void createExam(String type) {
        esame.setType(type);
    }

    @Override
    public void closeExam() {
        esame.setQuestionsNumber(counter);
    }

    @Override
    public void addQuestion(String question, int number) {
        counter++;
        esame.addQuestion(number, question);
    }

    @Override
    public void closeQuestion() { }

    @Override
    public void addOption(String option, int number) {
        esame.getQuestion(number).addOption(option);
    }

    @Override
    public void setResponse(String response, int number) {
        esame.getQuestion(number).setCorrectAnswer(Integer.parseInt(response));
    }

    //metodo non presente nell'interfaccia che, una volta costruito correttamente l'oggetto esame
    //restituisce l'ExamIF
    public ExamIF getExam(){
        return esame;
    }

}

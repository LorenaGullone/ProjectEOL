package model.exam;

import java.util.ArrayList;
import java.util.Collections;


public class Question {
    private int id;
    private String question;
    private ArrayList<String> options;
    private int correct_answer;

    public Question(int id, ArrayList<String> options, String question, int rightA) {
        this.id=id;
        this.options=options;
        this.question=question;
        this.correct_answer=rightA;
    }//costruttore1

    public Question(int id, String question){
        this.id=id;
        this.question=question;
        this.options=new ArrayList<>();
    }//costruttore2

    //metodi getters and setters

    //metodi che modificano lo stato intrinseco dell'oggetto per cui hanno visibilità ridotta
    void setCorrectAnswer(int correct_answer) {
        this.correct_answer=(correct_answer-1);
    }
    void setQuestion(String question){
        this.question=question;
    }
    boolean addOption(String option){
        if( option==null || options.contains(option) )
            return false;
        options.add(option);
        return true;
    }//addOption

    //metodi pubblici per accedere allo stato interno senza modificarlo
    public String getQuestion(){
        return question;
    }
    public int getCorrectAnswer() {
        return correct_answer;
    }
    public ArrayList<String> getOptions(){
        return new ArrayList<>(Collections.unmodifiableList(options));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Domanda: " + question + "\n");
        for( int i=0; i<options.size(); i++ ){
            int index=i+1;
            sb.append("Opzione " + index + ": " + options.get(i) + "\n");
        }
        sb.append("Risposta corretta: " + (correct_answer+1) + "\n");
        return sb.toString();
    }

}

package model.exam;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QuestionStateClient {
    private int id;
    private int options_number=0;
    private String question;
    private ArrayList<String> options;
    private int correct_answer;
    private QuestionStateIF currentState = QuestionState.NON_ATTIVA;
    private boolean active;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Lock lock = new ReentrantLock();
    private ScheduledFuture<?> timerControl;
    private Runnable timeoutTask;

    public QuestionStateClient(int id){
        this.id=id;
        this.options = new ArrayList<>();
    }//costruttore1
    public QuestionStateClient(int id, String question){
        this.id=id;
        this.question=question;
        this.options=new ArrayList<>();
        this.options_number=0;
    }//costruttore2

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question=question;
    }
    public String getOption(int index){
        return options.get(index);
    }

    public boolean addOption(String option){
        if( option==null || options.contains(option) )
            return false;
        options.add(option);
        options_number++;
        return true;
    }

    public String innerState(){
        if( currentState.equals(QuestionState.NON_ATTIVA))
            return "NON_ATTIVA";
        else if (currentState.equals(QuestionState.VALIDA))
            return "ATTIVA";
        else if (currentState.equals(QuestionState.PRESENTATA))
            return "PRESENTATA";
        else if (currentState.equals(QuestionState.CHIUSA))
            return "CHIUSA";
        else
            return null;
    }

    public void setActive(Boolean b){
        this.active=b;
    }
    public boolean getActive(){
        return active;
    }

    private interface QuestionStateIF {
        default void entryAction(QuestionStateClient q){ }
        default void ready(QuestionStateClient q){ }
        default void expired(QuestionStateClient q) { }
        default void submitted(QuestionStateClient q){ }
        default void close(QuestionStateClient q){ }
    }

    private enum QuestionState implements QuestionStateIF {
        NON_ATTIVA{
            @Override
            public void ready(QuestionStateClient q) {
                System.out.println("Transizione ad attiva");
                q.transition(VALIDA);
            }
        },
        VALIDA {
            @Override
            public void entryAction( QuestionStateClient q ){
                q.setActive(true);
                System.out.println("Attiva");
                q.startTimer();
            }
            @Override
            public void expired(QuestionStateClient q){
                System.out.println("Tempo scaduto: transizione a chiusa");
                q.transition(CHIUSA);
            }

            @Override
            public void submitted(QuestionStateClient q) {
                System.out.println("Transizione a presentata");
                q.transition(PRESENTATA);
            }
        },
        CHIUSA {
            @Override
            public void entryAction(QuestionStateClient q) {
                System.out.println("Chiusa");
                q.setActive(false);
            }
        },
        PRESENTATA {
            @Override
            public void entryAction(QuestionStateClient q) {
                System.out.println("Presentata");
                q.cancelTimer();
            }

            @Override
            public void close(QuestionStateClient q) {
                q.transition(CHIUSA);
            }
        }
    }//QuestionState

    private final void transition(QuestionStateIF nextState) {
        currentState = nextState;
        currentState.entryAction(this);
    }// transition

    public void start() {
        lock.lock();
        try {
            currentState.ready(this);
        } finally {
            lock.unlock();
        }
    }

    void cancelTimer() {
        timerControl.cancel(true);
        currentState.close(this);
    }

    private void timeout() {
        lock.lock();
        try {
            currentState.expired(this);
        } finally {
            lock.unlock();
        }

    }

    private void startTimer() {
        if (timeoutTask == null) {
            timeoutTask = () -> timeout();
        } else {
            timerControl.cancel(true);
        }
        timerControl = executor.schedule(timeoutTask, 5, TimeUnit.MINUTES);
    }

    public void submit(){
        lock.lock();
        try{
            currentState.submitted(this);
        }finally {
            lock.unlock();
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Domanda: " + question + "\n");
        for( int i=0; i<options.size(); i++ ){
            int index=i+1;
            sb.append("Opzione " + index + ": " + options.get(i) + "\n");
        }
        sb.append("Risposta corretta: " + correct_answer + "\n");
        return sb.toString();

    }
}

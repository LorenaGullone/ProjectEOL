package model.exam;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractExam implements ExamIF {

    //variabili d'istanza
    protected Date startDate;
    protected boolean status;
    protected String type;
    protected long timeToLive;
    protected int id;
    protected int questions_number;
    protected Map<Integer, Question> test = new HashMap<>();

    //variabili di istanza utili per l'implementazione del pattern Observer
    private List<ExamListener> listeners = new LinkedList<>();

    //variabili di istanza utili per l'implementazione del pattern State
    private ExamStateIF currentState = ExamState.PRENOTAZIONI;
    private final Lock lock = new ReentrantLock();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> timerControl;
    private Runnable timeoutExam;


    //IMPLEMENTAZIONE PATTERN STATE

    //definizione di un'interfaccia comune per gli stati in cui si può trovare un oggetto ExamIF
    //utilizzo combinato di State con Flyweight e Singleton: i metodi per reagire al cambiamento di stato ricevono il contesto come parametro e
    //si ha un numero limitato di possibili configurazioni di stato ciascuno dei quali corrisponde ad un'istanza di una enumerazione
    //Il contesto corrisponde all'oggetto AbstractExam del quale si va a cambiare lo stato
    private interface ExamStateIF {
        default void entryAction(AbstractExam e){ }
        default void exitAction(AbstractExam e){ }
        default void timeout(AbstractExam e){ }
    }//StateExamIF

    private enum ExamState implements ExamStateIF {
        PRENOTAZIONI {
            @Override
            public void entryAction(AbstractExam e){
                System.out.println("Fase prenotazioni");
                e.status=false;
                e.startTimer(new Date(e.getStartDate()));
            }
            @Override
            public void timeout(AbstractExam e){
                e.transition(ESAME_AVVIATO);
            }
        },
        ESAME_AVVIATO{
            @Override
            public void entryAction(AbstractExam e){
                System.out.println("Inizio esame");
                e.status=true;
                e.startTimer(new Date(e.getStartDate()+e.timeToLive));
                System.out.println(new Date(e.getStartDate()+e.timeToLive).toString());
                e.notifyListener(new ExamEvent(e));
            }

            @Override
            public void timeout(AbstractExam e){
                e.transition(CHIUSURA_ESAME);
            }
        },
        CHIUSURA_ESAME{
            @Override
            public void entryAction(AbstractExam e){
                System.out.println("Fine esame");
                e.status=false;
            }
        }

    }//ExamState


    //metodi della classe AbstractExam

    //IMPLEMENTAZIONE DEI METODI PER LA GESTIONE DELLO STATO
    private void transition(ExamStateIF nextState){
        if( currentState!=null )
            currentState.exitAction(this);
        currentState = nextState;
        currentState.entryAction(this);
    }//transition

    private void timeout(){
        lock.lock();
        try{
            currentState.timeout(this);
        } finally {
            lock.unlock();
        }
    }//timeout

    private void startTimer(Date time){
        if( timeoutExam == null ){
            timeoutExam = () -> timeout();
        } else {
            timerControl.cancel(true);
        }
        timerControl = executor.schedule(timeoutExam, time.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }//startTimer

    @Override
    public void start() {
        lock.lock();
        try {
            currentState.entryAction(this);
        } finally {
            lock.unlock();
        }
    }

    //IMPLEMENTAZIONE PATTERN OBSERVER
    //IMPLEMENTAZIONE DEI METODI PER LA SOTTOSCRIVER O RIMUOVERE UN OSSERVATORE
    @Override
    public synchronized void addExamListener(ExamListener listener){
        if( listener==null || listeners.contains(listener) )
            return;
        listeners.add(listener);
    }//addExamListener

    @Override
    public synchronized void removeExamListener(ExamListener listener){
        if( listener==null || !listeners.contains(listener) )
            return;
        listeners.remove(listener);
    }//removeExamListener

    protected void notifyListener(ExamEvent e) {
        for (ExamListener examListener : listeners)
            examListener.examChanged(e);
    }//notifyListeners


    //IMPLEMENTAZIONE DEI METODI DELL'INTERFACCIA
    public int getID(){
        return id;
    }//getId

    public boolean getStatus(){
        return status;
    }//getStatus

    public long getStartDate(){
        return startDate.getTime();
    }//getStartDate

    public String getType(){
        return type;
    }//getType

    public long getTTL(){
        return timeToLive;
    }//getTTL

    public Map<Integer, Question> getTest(){
        return Collections.unmodifiableMap(test);
    }//getTest

    @Override
    public void setType(String type) {
        this.type=type;
    }//setType

    public void setStartDate(Date start){
        this.startDate=start;
    }//setStartDate

    public void setQuestionsNumber(int number){
        this.questions_number=number;
    }//setQuestionsNumber

    public void setID(int id){
        this.id=id;
    }//setID
    @Override
    public void setTTL(long time) {
        this.timeToLive=time;
    }//setTTL (durata)

    @Override
    public int getQuestionsNumber() {
        return this.questions_number;
    }//getQuestionsNumber

    public String toString(){
        StringBuilder sb = new StringBuilder();
        long durata_minuti = timeToLive/60000;
        sb.append(type);
        sb.append("    ");
        sb.append(startDate.toString());
        sb.append("    ");
        sb.append(durata_minuti + " minuti");
        return sb.toString();
    }//toString

    //i seguenti due metodi sono utilizzati dal builder per costruire l'oggetto nelle fasi iniziali
    //si tratta di metodi con visibilità ridotta rispetto agli altri
    void addQuestion(int number, String question) {
        test.put(number, new Question(number, question));
    }//addQuestion

    Question getQuestion(int question) {
        return test.get(question);
    }//getQuestion

}//AbstractExam

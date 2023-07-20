package org.example;

import controller.StudentController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.exam.ExamIF;
import model.exam.MultipleChoiceExam;
import model.exam.QuestionStateClient;
import proto.ServerserviceGrpc;
import proto.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Student {
    private Map<Integer, ExamIF> esami_disponibili = new HashMap<>();
    private Map<Integer, ExamIF> esami_prenotati = new HashMap<>();
    private Map<Integer, ExamIF> esami_sostenuti = new HashMap<>();
    private ManagedChannel managedChannel;
    //canale di comunicazione con il server remoto
    private ServerserviceGrpc.ServerserviceBlockingStub stub;
    //oggetto stub che funge da proxy
    private ExamIF esameCorrente;
    private int port;
    private InetAddress address;
    private int serverPort;
    private int matricola;
    private String cf;
    private StudentController studentController;
    private int domandaCorrente;

    public Student(int port, int serverPort, InetAddress IP) throws UnknownHostException {
        this.port=port;
        this.serverPort=port;
        this.address=IP;
        //il canale viene costruito specificando indirizzo e numero di porta del server del server
        this.managedChannel = ManagedChannelBuilder.forAddress("localhost", serverPort).usePlaintext().build();
        this.stub = ServerserviceGrpc.newBlockingStub(managedChannel);
    }//costruttore

    //metodi getters and setters per accedere ai campi di interesse che identificano uno Student
    public void setMatricola(int matricola){
        this.matricola=matricola;
    }//setMatricola
    public void setCf(String cf){
        this.cf=cf;
    }//setCF
    public int getMatricola(){ return matricola; }//getMatricola
    public String getCf() {return cf;}//getCf

    //metodo per il settaggio dello StudentController
    public void setStudentController(StudentController studentController){
        this.studentController = studentController;
    }//setStudentController

    //metodo che restituisce una lista non modificabile degli esami prenotati
    public Map<Integer, ExamIF> esamiPrenotati(){
        return Collections.unmodifiableMap(esami_prenotati);
    }//esamiPrenotati

    //metodo che permette di conoscere se la domanda corrente corrisponde all'ultima domanda dell'esame corrente
    public boolean ultimaDomanda(){ return domandaCorrente>esameCorrente.getQuestionsNumber(); }//ultimaDomanda

    //metodo per impostare un esame come esame corrente che si sta svolgendo
    public void setEsameCorrente(ExamIF e){
        this.esameCorrente=e;
        //si parte da 0: le domande partono da 1 dunque nel richiedere la prossima domanda questo indice viene incrementato come prima istruzione
        this.domandaCorrente=0;
    }//setEsameCorrente

    //stub1 per available_exams --> metodo che permette di visualizzare la lista degli esami disponibili
    public Map<Integer, ExamIF> esamiDisponibili(){
        try{
            Map<Integer, ExamIF> esami = new HashMap<>();
            //poichè viene generato uno strem di messaggi di risposta si usa un iteratore di oggetti di tipo ExamReply (messaggio di risposta del metodo)
            //per ottenere una risposta si usa lo stub che richiama il metodo availableExam del server in cui è specificata una richiesta di tipo ExamRequest
            Iterator<Services.ExamsReply> reply = stub.availableExams(Services.ExamsRequest.newBuilder().build());
            while(reply.hasNext()){
                //finchè si ha una risposta si può costruire un oggetto ExamIF accedendo ai parametri del messaggio di risposta
                Services.ExamsReply single_reply = reply.next();
                MultipleChoiceExam es = new MultipleChoiceExam(new Date(single_reply.getStartDate()));
                es.setID(single_reply.getIdEsame());
                es.setType(single_reply.getType());
                es.setTTL(single_reply.getDurata());
                es.setQuestionsNumber(single_reply.getQuestions());
                esami.put(single_reply.getIdEsame(), es);
            }
            return esami;
        }catch (Exception e){
            System.out.println("Errore");
            return null;
        }
    }//esamiDisponibili

    //stub2 per exam_booking --> metodo che permette di prenotare un esame specificandone l'id
    public boolean prenota(int idEsame){
        //viene creato un messaggio di risposta di tipo BookingReply a partire da uno stub del metodo examBooking che prende come argomento una richiesta BookingRequest
        //la richiesta BookingRequest viene costruita utilizzando i valori dello studente che si registra, in questa sede, presso il server
        Services.BookingReply reply = stub.examBooking(Services.BookingRequest.newBuilder().setIdEsame(idEsame).setMatricola(matricola).setCf(cf).setPort(port).setAddress(address.getHostAddress()).build());
        if( reply.getNumPrenotazione()>=0 ){
            //se la richiesta di prenotazione va a buon fine, la risposta avrà come numero di prenotazione un numero non negativo
            //l'esame viene dunque aggiunto agli esami prenotati e rimosso da quelli disponibili
            ExamIF prenotato = esami_disponibili.get(idEsame);
            esami_prenotati.put(reply.getNumPrenotazione(), prenotato);
            esami_disponibili.remove(idEsame);
            return true;
        } else {
            //se il numero è negativo la prenotazione è stat rifiutata
            return false;
        }
    }//prenota

    //stub3 per start_exam --> metodo che permette di iniziare un esame, se attivo
    public boolean iniziaEsame(int idEsame){
        if( esami_sostenuti.containsKey(idEsame)){
            //se l'esame è già stato sostenuto non si rende possibile iniziarlo nuovamente
            return false;
        }
        //viene creato un messaggio di risposta di tipo StartReply a partire da uno stub del metodo startExam che prende come argomento una richiesta StartRequest
        //la richiesta StartRequest viene costruita utilizzando i valori dello studente e l'id dell'esame
        Services.StartReply reply = stub.startExam(Services.StartRequest.newBuilder().setIdEsame(idEsame).setMatricola(matricola).setCf(cf).build());
        //il messaggio di risposta contiene un campo che indica se è possibile procedere o meno con la compilazione dell'esame
        boolean iniziato = reply.getProcedi();
        if(iniziato==true) {
            //se l'esame è attivo lo si rimuove dai prenotati
            esami_prenotati.remove(idEsame);
            return true;
        } else
            return false;
    }//iniziaEsame

    //stub4 per next_question --> metodo che permette di accedere alla prossima domanda dell'esame
    public QuestionStateClient prossimaDomanda(){
        //la prossima domanda da chiedere corrisponde a domandaCorrente (incrementata) ed è relativa a esameCorrente
        domandaCorrente++;
        System.out.println(domandaCorrente);
        if( domandaCorrente>esameCorrente.getQuestionsNumber() ){
            //sono finite le domande d'esame
            return null;
        } else {
            //se le domande dell'esame corrente non sono finite
            //viene creato un messaggio di risposta di tipo NextQuestionReply a partire da uno stub del metodo nextQuestion che prende come argomento una richiesta NextQuestionRequest
            //la richiesta NextQuestionRequest viene costruita utilizzando i valori dello studente, dell'esameCorrete e della domandaCorrente
            //nota la risposta da parte del server si costruisce un oggetto di tipo QuestionStateClient da restituire
            QuestionStateClient question = new QuestionStateClient(domandaCorrente);
            Services.NextQuestionReply reply = stub.nextQuestion(Services.NextQuestionRequest.newBuilder().setIdEsame(esameCorrente.getID()).setNumberQuestion(domandaCorrente).build());
            question.setQuestion(reply.getQuestion());
            question.addOption(reply.getOption1());
            question.addOption(reply.getOption2());
            question.addOption(reply.getOption3());
            return question;
        }
    }//prossimaDomanda

    //stub4 per submit_answer --> metodo che permette di inviare la risposta selezionata al server
    public boolean inviaRisposta(int answer){
        //viene creato un messaggio di risposta di tipo AnswerReply a partire da uno stub del metodo submitAnswer che prende come argomento una richiesta AnswerRequest
        //la richiesta AnswerReqeust viene costruita utilizzando i valori dello studente, dell'esameCorrete e della domandaCorrente
        //nota la risposta da parte del server si verifica se questa è stata o meno processata e se l'esame si è o meno concluso
        Services.AnswerReply reply = stub.submitAnswer(Services.AnswerRequest.newBuilder().setMatricola(matricola).setIdEsame(esameCorrente.getID()).setQuestion(domandaCorrente-1).setAnswer(answer).build());
        if(reply.getProcessata() && !reply.getTempoScaduto())
            return true;
        else
            return false;
    }//inviaRisposta

    //stub5 per result --> metodo che permette di visualizzare il punteggio ottenuto fornendo le risposte precedenti
    public int ottieniRisultato(){
        //viene creato un messaggio di risposta di tipo ResultReply a partire da uno stub del metodo result che prende come argomento una richiesta ResultRequest
        //la richiesta ResultRequest viene costruita utilizzando i valori dello studente e dell'esameCorrete
        //la risposta del server ResultReply contiene un intero che corrisponde al punteggio ottenuto
        //noto il punteggio l'esameCorrente viene inserito nella lista degli esami sostenuti
        Services.ResultReply reply = stub.result(Services.ResultRequest.newBuilder().setMatricola(matricola).setIdEsame(esameCorrente.getID()).build());
        int risultato = reply.getPunteggio();
        esami_sostenuti.put(esameCorrente.getID(), esameCorrente);
        return risultato;
    }//ottieniRisultato

    //stub6 per correction
    public Map<String, String> risposteEsatte(){
        try{
            Map<String, String> risposte = new HashMap<>();
            //domanda-risposta
            //poichè viene generato uno strem di messaggi di risposta si usa un iteratore di oggetti di tipo ModuleReply (messaggio di risposta del metodo)
            //per ottenere una risposta si usa lo stub che richiama il metodo correction del server in cui è specificata una richiesta di tipo ModuleRequest
            Iterator<Services.ModuleReply> reply = stub.correction(Services.ModuleRequest.newBuilder().setIdEsame(esameCorrente.getID()).build());
            while(reply.hasNext()){
                Services.ModuleReply single_reply = reply.next();
                risposte.put(single_reply.getQuestion(), single_reply.getAnswer());
            }
            return risposte;
        }catch (Exception e){
            System.out.println("Errore");
            return null;
        }
    }//risposteEsatte

}//Student

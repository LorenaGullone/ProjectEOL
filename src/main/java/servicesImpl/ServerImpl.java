package servicesImpl;

import io.grpc.stub.StreamObserver;
import model.exam.ExamIF;
import model.exam.Question;
import org.example.Server;
import proto.ServerserviceGrpc;
import proto.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class ServerImpl extends ServerserviceGrpc.ServerserviceImplBase {
    Server server;
    public ServerImpl(Server server){
        this.server=server;
    }//costruttore

    @Override
    public void availableExams(Services.ExamsRequest request, StreamObserver<Services.ExamsReply> responseObserver) {
        //metodo che permette di visualizzare gli esami disponibili nel sistema:
        //viene eseguita una scansione nella lista degli esami disponibili inseriti nel server e per ogni esame si costruisce una risposta da inviare sullo stream
        //settando con le informazioni necessarie i parametri del messaggio
        for (ExamIF exam : server.getEsamiDisponibili().values()){
            Services.ExamsReply reply = Services.ExamsReply.newBuilder().setIdEsame(exam.getID()).setType(exam.getType()).setStartDate(exam.getStartDate()).setStatus(exam.getStatus()).setQuestions(exam.getQuestionsNumber()).setDurata(exam.getTTL()).build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }//avaibleExam

    @Override
    public void examBooking(Services.BookingRequest request, StreamObserver<Services.BookingReply> responseObserver) {
        //metodo che permette di prenotare un esame
        //la richiesta contiene l'id dell'esame da prenotare insieme alle generalità dello studente
        try {
            //il metodo prenota richiamato sull'oggetto server restituisce un intero che risulta non negativo solo se la prenotazione ha avuto successo
            //l'intero restituito corrisponde al numero di prenotazione
            int reservation = server.prenota(request.getIdEsame(), request.getMatricola(), request.getCf(), InetAddress.getByName(request.getAddress()), request.getPort());
            System.out.println("Studente " + request.getMatricola() + " " + request.getCf() + " prenotato per esame " + request.getIdEsame() + "con numero prenotazione " + reservation);
            Services.BookingReply reply = Services.BookingReply.newBuilder().setNumPrenotazione(reservation).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }//examBooking

    @Override
    public void startExam(Services.StartRequest request, StreamObserver<Services.StartReply> responseObserver) {
        Services.StartReply reply;
        //metodo che permette di iniziare un esame
        if( server.getEsamiDisponibili().containsKey(request.getIdEsame()) ){
            //se l'esame è negli esami disponibili ancora non è iniziato per cui non è possibile accedervi
            //(appena l'esame iniziail server che è un ExamListener lo sposta nella lista degli esami iniziati)
            reply = Services.StartReply.newBuilder().setProcedi(false).build();
        } else {
            //se l'esame non è nella lista degli esami disponibili è iniziato e il metodo costruisce una risposta
            //restituendo lo stato dell'esame (true se l'esame può essere ancora svolto, false se è scaduto)
            reply = Services.StartReply.newBuilder().setProcedi(server.getEsamiIniziati().get(request.getIdEsame()).getStatus()).build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }//startExam

    @Override
    public void nextQuestion(Services.NextQuestionRequest request, StreamObserver<Services.NextQuestionReply> responseObserver) {
        //metodo che permette di visualizzare la prossima domanda del test d'esame
        //la richiesta prevede venga specificato l'id dell'esame e l'indice della prossima domanda
        //il server preleva l'esame, il test e la domanda e costruisce la risposta specificando la domanda e le opzioni possibili
        //in questa sede non viene fatto il controllo rispetto alla possibilità che l0esame sia termimato: tale controllo è effettuato
        //quando si tenta di sottomettere la risposta con il metodo submitAnswer
        ExamIF esame = server.getEsamiIniziati().get(request.getIdEsame());
        Map<Integer, Question> domande = esame.getTest();
        Question domanda = domande.get(request.getNumberQuestion());
        Services.NextQuestionReply reply = Services.NextQuestionReply.newBuilder()
                .setNumberQuestion(request.getNumberQuestion())
                .setQuestion(domanda.getQuestion())
                .setOption1(domanda.getOptions().get(0))
                .setOption2(domanda.getOptions().get(1))
                .setOption3(domanda.getOptions().get(2))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }//nextQuestion

    @Override
    public void submitAnswer(Services.AnswerRequest request, StreamObserver<Services.AnswerReply> responseObserver) {
        //metodo che permette di confermare la propria risposta ad una domanda
        //la richiesta contiene l'id dell'esame, l'indice della domanda e l'indice della risposta
        //il metodo aggiungiRisposta del server riceve tali parametri e restituisce un booleano che indica se l'inserimento è andato o meno a buon fine
        //l'inserimento non va a buon fine se l'esame è scaduto
        boolean riscontro = server.aggiungiRisposta(request.getMatricola(), request.getQuestion(), request.getAnswer(), request.getIdEsame());
        Services.AnswerReply reply;
        if( riscontro ){
            reply = Services.AnswerReply.newBuilder().setProcessata(true).setTempoScaduto(false).build();
        } else {
            reply = Services.AnswerReply.newBuilder().setProcessata(false).setTempoScaduto(true).build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }//submitAnswer

    @Override
    public void result(Services.ResultRequest request, StreamObserver<Services.ResultReply> responseObserver) {
        //metodo che permette di ottenere il punteggio per le risposte inserite
        //viene invocata la funzione getPunteggio di server che, specificano l'id dell'esame e la matricola valuta le risposte precedentemente inserite
        int punteggio = server.getPunteggio(request.getIdEsame(), request.getMatricola());
        Services.ResultReply reply = Services.ResultReply.newBuilder().setIdEsame(request.getIdEsame()).setPunteggio(punteggio).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }//result

    @Override
    public void correction(Services.ModuleRequest request, StreamObserver<Services.ModuleReply> responseObserver) {
        //metodo che permette di ottenere le risposte corrette alle domande dell'esame svolto
        //per ogni domanda dell'esame è costruito un messaggio di risposta da inviare sullo stream che contiene la domanda e la risposta esatta
        for( Question question : server.getEsamiIniziati().get(request.getIdEsame()).getTest().values()){
            int rispostaCorretta = question.getCorrectAnswer();
            Services.ModuleReply reply = Services.ModuleReply.newBuilder().setQuestion(question.getQuestion()).setAnswer(question.getOptions().get(rispostaCorretta)).build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }//correction

}//ServerImpl

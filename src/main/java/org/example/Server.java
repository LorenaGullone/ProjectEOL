package org.example;

import controller.ServerController;
import model.exam.ExamEvent;
import model.exam.ExamIF;
import model.exam.ExamListener;
import model.student.StudentRecord;

import java.net.InetAddress;
import java.util.*;

public class Server implements ExamListener {

    private ServerController serverController;
    private Map<Integer, ExamIF> esami_disponibili = new HashMap<>();
    private Map<Integer, ExamIF> esami_iniziati = new HashMap<>();
    private Map<Integer, Map<Integer, StudentRecord>> prenotati = new HashMap<>();
    //esame-matricola-studente
    private Map<Integer, Map<Integer, ArrayList<Integer>>> punteggi = new HashMap<>();
    //matricola-esame-lista risposte

    public void setServerController(ServerController s){
        this.serverController=s;
    }

    public void addExam(int id, ExamIF exam){
        //metodo per l'aggiunta di un esame
        //l'esame viene inserito negli esami disponibili e, su questo, è invocato il metodo start che avvia la fase di prenotazioni
        //allo scadere della fase di prenotazioni l'esame inizia e cambia il proprio stato interno
        esami_disponibili.put(id, exam);
        exam.start();
        //il server si sottoscrive come ascoltatore dell'esame
        exam.addExamListener(this);
        //viene inizializzata l'apposita hashmap per le prenotazioni
        HashMap<Integer, StudentRecord> prenotazioni = new HashMap<>();
        prenotati.put(id, prenotazioni);
    }//addExam

    public int prenota(int idEsame, int matricola, String cf, InetAddress address, int port){
        //metodo che permette di prenotare un esame
        //è possibile effettuare la prenotazione solo se l'esame è disponibile e non si è già registrati come prenotati all'esame
        if(!esami_disponibili.containsKey(idEsame) || prenotati.get(idEsame).containsKey(matricola)) {
            return -1;
        } else {
            StudentRecord studente = new StudentRecord(matricola, cf, address, port);
            prenotati.get(idEsame).put(matricola, studente);
            System.out.println(studente);
            System.out.println(prenotati);
            //mel momento in cui uno studente si prenota viene inizializzata anche la entry relativa ai punteggi dello studente se non esistente
            //inizialmente, per ogni esame prenotato, le risposte sono inizializzate a -1
            //il valore viene poi aggiornato nel corso dello svolgimento dell'esame
            if( !punteggi.containsKey(studente) ){
                ArrayList<Integer> risposte_esame = new ArrayList<>();
                for(int i = 0; i< esami_disponibili.get(idEsame).getQuestionsNumber(); i++)
                    risposte_esame.add(-1);
                Map<Integer, ArrayList<Integer>> risposte = new HashMap<>();
                risposte.put(idEsame, risposte_esame);
                punteggi.put(matricola, risposte);
            } else {
                ArrayList<Integer> risposte_esame = new ArrayList<>();
                for(int i = 0; i< esami_disponibili.get(idEsame).getQuestionsNumber(); i++)
                    risposte_esame.add(-1);
                punteggi.get(matricola).put(idEsame, risposte_esame);
            }
            return prenotati.get(idEsame).size();
        }
    }//prenota

    //metodi per accedere in sola scrittura alla lista degli esami disponibili e iniziati
    public Map<Integer, ExamIF> getEsamiDisponibili(){
        return Collections.unmodifiableMap(this.esami_disponibili);
    }//getEsamiDisponibili
    public Map<Integer, ExamIF> getEsamiIniziati(){
        return Collections.unmodifiableMap(this.esami_iniziati);
    }//getEsamiIniziati

    public boolean aggiungiRisposta(int matricola, int question, int answer, int idEsame){
        //metodo che permette di aggiungere una risposta data ad un esame
        //prima di procedere con l'inserimento della risposta si verifica che l'esame non sia terminato per cui si controlla lo stato
        //successimente si procede con l'inserimento accedendo alla mappa dei punteggi e aggiornando le risposte
        if( esami_iniziati.get(idEsame).getStatus()==true ){
            System.out.println(esami_iniziati.get(idEsame).getStatus());
            punteggi.get(matricola).get(idEsame).add(question, answer);
            System.out.println(matricola + " " + question + " " +answer );
            return true;
        } else {
            return false;
        }
    }//aggiungiRisposta

    public int getPunteggio(int idEsame, int matricola){
        //metodo che permette di ottenere il punteggio realizzato a seguito delle risposte fornite per un esame
        //il metodo preleva dalla mappa dei punteggi la lista delle risposte date associate alla matricola e all'esame indicati
        //la lista delle risposte viene passata come parametro del metodo valutaRisposte specificato in MultipleChoiceExam
        ArrayList<Integer> risposte = punteggi.get(matricola).get(idEsame);
        int valutazione = esami_iniziati.get(idEsame).valutaRisposte(risposte);
        return valutazione;
    }//getPunteggio

    @Override
    public void examChanged(ExamEvent e) {
        //metodo dell'interfaccia ExamListener che permette al server di reagire al cambiamento di stato di un esame
        //da PRENOTAZIONI ad ESAME_AVVIATO
        //il metodo sposta l'esame dalla lista degli esami disponibili alla lista degli esami iniziati
        ExamIF esameIniziato = e.getSource();
        System.out.println("esame iniziato e spostato");
        esami_disponibili.remove(esameIniziato.getID());
        esami_iniziati.put(esameIniziato.getID(), esameIniziato);
        System.out.println("Esami disponibili:= " + esami_disponibili);
        System.out.println("Esami iniziati:= " + esami_iniziati);
    }//examChanged

}//Server

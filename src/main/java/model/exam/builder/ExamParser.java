package model.exam.builder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ExamParser {
    private final String urlString;
    private BufferedReader br;
    private StringTokenizer st;
    private int nLine=0;
    private final ExamBuilder builder;
    private String token;
    private int numAnswer=0;
    private int counter=0;

    public ExamParser(ExamBuilder builder, String urlString){
        this.builder=builder;
        this.urlString=urlString;
    }//costruttore

    public void build(){
        try{
            doParse();
        } catch ( ExamParseException e ){
            e.printStackTrace();
        } catch ( FileNotFoundException e ) {
            throw new RuntimeException(e);
        }
    }//build

    //metodo che permette di scansionare il file di testo token per token utilizzando lo StringTokenizer
    private String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            String line = null;
            try {
                line = br.readLine();
                nLine++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null)
                return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }//nextToken

    //metodo che accede al file il lettura tramite BufferedReader
    private void doParse() throws ExamParseException, FileNotFoundException {
        br = new BufferedReader( new FileReader(urlString) );
        readDocument();
    }//doParse

    //metodi per la lettura del documento e riconoscimento dei token: in base allo specifico token si procede con
    //l'utilizzo dei metodi del builder che permettono di costruire un oggetto di tipo ExamIF
    private void readDocument() throws ExamParseException {
        token = nextToken();
        if( token==null )
            throw new ExamParseException("Atteso un token");
        if( !token.equals("<Esame>"))
            throw new ExamParseException("line " + nLine+ " Atteso: <Esame> trovato:" + token);
        String exam = readText();
        builder.createExam(exam);
        if( token==null )
            throw new ExamParseException("Atteso token");
        while (!token.equals("</Esame>")) {
            if (!token.equals("<Domanda>"))
                throw new ExamParseException("line " + nLine + " Atteso: <Domanda> trovato:" + token);
            counter++;
            numAnswer=0;
            readDomanda();
            token = nextToken();
            if (token == null)
                throw new ExamParseException("Atteso token");
        }
        builder.closeExam();
    }//readDocument

    public String readText() throws ExamParseException {
        StringBuilder par = new StringBuilder();
        token = nextToken();
        if (token == null)
            throw new ExamParseException("line " + nLine + " Atteso token");
        while (token.charAt(0) != '<') {
            par.append(" ").append(token);
            //par += " " + token;
            token = nextToken();
            if (token == null)
                throw new ExamParseException("line " + nLine + " Atteso token");
        }
        return par.toString();
    }//readText

    public void readDomanda() throws ExamParseException {
        if (token == null)
            throw new ExamParseException("Atteso token");
        token = nextToken();
        while (!token.equals("</Domanda>")) {
            if (token.equals("<Testo>")) {
                readTestoDomanda();
            } else if (token.equals("<Opzione>")) {
                readOpzione();
            } else if (token.equals("<Risposta>")) {
                if (numAnswer < 1)
                    readRisposta();
                else
                    throw new ExamParseException("Attesa una singola risposta");
            } else
                throw new ExamParseException("line " + nLine + " Atteso: <Testo> o <Opzione> o <Risposta> trovato:" + token);
            token = nextToken();
            if (token == null)
                throw new ExamParseException("Atteso token");
        }
        builder.closeQuestion();
    }//readDomanda

    public void readTestoDomanda() throws ExamParseException {
        StringBuilder par = new StringBuilder();
        token = nextToken();
        if (token == null)
            throw new ExamParseException("line " + nLine + " Atteso token");
        while (!token.equals("</Testo>")) {
            par.append(" ").append(token);
            token = nextToken();
            if (token == null)
                throw new ExamParseException("line " + nLine + " Atteso token");
        }
        builder.addQuestion(par.toString(), counter);
    }//readTestoDomanda

    public void readOpzione() throws ExamParseException {
        StringBuilder par = new StringBuilder();
        token = nextToken();
        if (token == null)
            throw new ExamParseException("line " + nLine + " Atteso token");
        while (!token.equals("</Opzione>")) {
            par.append(" ").append(token);
            token = nextToken();
            if (token == null)
                throw new ExamParseException("line " + nLine + " Atteso token");
        }
        builder.addOption(par.toString(), counter);
    }//readOpzione

    public void readRisposta() throws ExamParseException {
        StringBuilder par = new StringBuilder();
        token = nextToken();
        if (token == null)
            throw new ExamParseException("line " + nLine + " Atteso token");
        while (!token.equals("</Risposta>")) {
            par.append(token);
            token = nextToken();
            if (token == null)
                throw new ExamParseException("line " + nLine + " Atteso token");
        }
        numAnswer++;
        builder.setResponse(par.toString(), counter);
    }//readRisposta

}//ExamParser

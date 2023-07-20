package model.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MultipleChoiceExam extends AbstractExam {

    public MultipleChoiceExam(Date startDate) {
        this.startDate = startDate;
        this.test = new HashMap<>();
    }//costruttore

    @Override
    public int valutaRisposte(ArrayList<Integer> risposte) {
        int punteggio = 0;
        for (int i = 0; i < test.size(); i++) {
            int risposta_data = risposte.get(i);
            System.out.println("risposta data= " + risposta_data);
            int risposta_esatta = test.get(i+1).getCorrectAnswer();
            System.out.println("risposta esatta= " + risposta_esatta);
            if (risposta_data == -1) {
                //risposta non data
                punteggio = punteggio - 1;
            } else if (risposta_data == risposta_esatta) {
                //risposta esatta
                punteggio = punteggio + 3;
            } else {
                //risposta sbagliata
            }
        }
        return punteggio;
    }//valutaRisposta

}//MultipleChoiceExam
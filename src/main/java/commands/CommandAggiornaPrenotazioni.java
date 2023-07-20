package commands;

import model.exam.ExamIF;
import org.example.Student;

import javax.swing.*;
import java.util.Map;

public class CommandAggiornaPrenotazioni implements Command{
    private DefaultListModel<ExamIF> esami_prenotati;
    private Student student;
    private ExamIF esame;

    public CommandAggiornaPrenotazioni(Student student, DefaultListModel<ExamIF> esamiPrenotati, ExamIF selectedValue) {
        this.student=student;
        this.esami_prenotati=esamiPrenotati;
        this.esame=selectedValue;
    }//costruttore

    @Override
    public void execute() {
        //se i passi sono stati eseguiti correttamente questa asser dovrebbe essere sempre verificata
        assert student.getCf() != null && student.getMatricola() != 0;
        if( student.esamiPrenotati().containsKey(esame.getID())){
            //se l'esame è già contenuto nella lista degli esami prenotati dallo studente non lo si inserisce
            JOptionPane.showMessageDialog(null, "Esame già prenotato");
        } else {
            //se l'esame non è presente allora si invoca il metodo prenota di student che restituisce true nel caso
            //in cui la prenotazione ha successo: se la prenotazione ha successo si aggiorna la lista altrimenti
            //nota--> il metodo prenota si occupa di aggiornare la lista interna di student
            if (!esami_prenotati.contains(esame)) {
                if (student.prenota(esame.getID())) {
                    esami_prenotati.addElement(esame);
                } else {
                    JOptionPane.showMessageDialog(null, "Non è più possibile prenotare l'esame");
                }
            }
        }
    }//execute

}//CommandAggiornaPrenotazioni

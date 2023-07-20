package commands;

import model.exam.ExamIF;
import org.example.Student;

import javax.swing.*;
import java.util.Map;

public class CommandCaricaEsami implements Command {
    private DefaultListModel<ExamIF> esami_disponibili;
    private Student student;

    public CommandCaricaEsami(Student s, DefaultListModel<ExamIF> esami_disponibili){
        this.student=s;
        this.esami_disponibili=esami_disponibili;
    }

    @Override
    public void execute() {
        //il metodo aggiorna la lista degli esami disponibili consultando il server
        //la lista degli esami disponibili viene prelevata dal sever tramite la funzione esamiDispoibili che esegue una chiamata
        //a procedura remota
        //il metodo aggiorna la DefaultModelList in modo che le modifiche vengano visualizzate real time nella componente grafica
        Map<Integer, ExamIF> esami = student.esamiDisponibili();
        esami_disponibili.clear();
        for( int i: esami.keySet() ){
            esami_disponibili.addElement(esami.get(i));
        }
    }//execute

}//CommandCaricaEsami

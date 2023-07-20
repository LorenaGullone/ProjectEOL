package commands;

import controller.ExamController;
import org.example.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CommandSvolgiEsame implements Command {
    private Student student;
    private JFrame parent;
    private ExamController examController;

    public CommandSvolgiEsame(Student student, JFrame parent, ExamController controller) {
        this.student=student;
        this.parent=parent;
        this.examController=controller;
    }//costruttore

    private static boolean consensoUscita(){
        int option=JOptionPane.showConfirmDialog( null, "Continuare? ","Uscendo si perderanno tutti i dati e non sarà possibile recuperare la sessione corrente!", JOptionPane.YES_NO_OPTION);
        return option==JOptionPane.YES_OPTION;
    }//consensoUscita

    @Override
    public void execute() {
        //la finestra principale non è resa visibile e al suo posto viene inizializzata una nuova finestra
        //che contiene l'examController passato come argomento al costruttore
        parent.setVisible(false);
        JFrame frame = new JFrame();
        frame.setTitle("Svolgimento esame");
        frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        //alla chiusura della finestra, dopo aver chiesto conferma per uscire,
        //viene abilitata la finestra principale e chiusa quella corrente
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if( consensoUscita() ) {
                    parent.setVisible(true);
                    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                }
            }
        } );

        frame.add(examController);
        frame.pack();
        frame.setSize(1000,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(1, 1));
        frame.setVisible(true);
    }//execute

}//CommandSvolgiEsame

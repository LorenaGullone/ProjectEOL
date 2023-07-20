package view;

import controller.StudentController;
import model.exam.ExamIF;
import org.example.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class StudentGUI {

    public static void main(String [] args ) throws UnknownHostException {
        int studentPort = new Random().nextInt(1000)+9999;
        int serverPort = 9990;

        Student student = new Student(studentPort, serverPort, InetAddress.getLocalHost());;

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem howUse = new JMenuItem("How to use");
        howUse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Applicazione per la gestione di prenotazioni Esami OnLine. \n" +
                                "Premere 'Visualizza Esami' per accedere all'elenco di esami disponibili. \n" +
                                "Prima di prenotare un esame premere sul pulsante di 'Riconoscimento' per inserire i propri dati. \n" +
                                "Dopo la fase di riconoscimento è possibile selezionare un esame: per prenotarsi premere 'Prenota'. \n" +
                                "All'orario indicato come inizio dell'esame, premere 'Start' per visualizzare le domande.");
            }
        });
        helpMenu.add(howUse);
        frame.setJMenuBar(menuBar);

        StudentController studentController = new StudentController(student, frame);
        student.setStudentController(studentController);

        frame.add(studentController);


        //impostazioni della finestra
        frame.setTitle("Prenotazione Esami");
        frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if( consensoUscita() ) System.exit(0);
            }
        } );
        frame.pack();
        frame.setSize(1000,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(1, 1));
        frame.setVisible(true);
    }//main

    private static boolean consensoUscita(){
        int option=JOptionPane.showConfirmDialog( null, "Continuare? ","Uscendo si perderanno tutti i dati!", JOptionPane.YES_NO_OPTION);
        return option==JOptionPane.YES_OPTION;
    }//consensoUscita
}//StudentGUI

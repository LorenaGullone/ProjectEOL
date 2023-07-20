package controller;

import commands.CommandAggiornaPrenotazioni;
import commands.CommandCaricaEsami;
import commands.CommandCredenziali;
import commands.CommandSvolgiEsame;
import model.exam.ExamIF;
import org.example.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentController extends JPanel {
    private Student student;
    private JPanel infoPanel;
    //pannello in cui vengono visualizzate le informazioni relative agli esami
    private JPanel controlPanel;
    //pannello di controllo per le interazioni
    private JFrame parent;
    //riferimento alla finestra principale nella quale il controller è inserito
    private DefaultListModel<ExamIF> esami_disponibili = new DefaultListModel<>();
    //struttura dati dinamica per la visualizzazione degli esami_disponibili
    private DefaultListModel<ExamIF> esami_prenotati = new DefaultListModel<>();
    //struttura dati dinamica per la visualizzazione degli esami_prenotato
    private Font font = new Font("Century Gothic", Font.PLAIN, 13);

    public StudentController(Student student, JFrame parent){
        this.student=student;
        this.parent=parent;
        buildPanel();
    }//costruttore

    private void buildPanel(){
        //istanziazione dei pannelli da inserire nel pannello principale this
        infoPanel = new JPanel(new GridLayout(2, 1));
        controlPanel = new JPanel(new GridLayout(12, 3));

        //definizione del modello associato alla DefaultListModel di esami_disponibili
        //utilizzando JList i cambiamenti apportati al modello vengono immediatamente riflessi nella visualizzazione dell'interfaccia utente,
        // consentendo agli utenti di vedere in tempo reale le modifiche nella lista.
        JList<ExamIF> examListDisp = new JList<>();
        examListDisp.setModel(esami_disponibili);
        //aggiunta di una JScrollPane in cui inserire la lista di esami disponibili
        JScrollPane scrollPane1 = new JScrollPane(examListDisp);
        scrollPane1.setBorder( new TitledBorder(null, "Elenco Esami", TitledBorder.LEADING, TitledBorder.TOP, font, null));


        //definizione del modello associato alla DefaultListModel di esami_prenotati
        JList<ExamIF> examListPren = new JList<>();
        examListPren.setModel(esami_prenotati);
        //aggiunta di una JScrollPane in cui inserire la lista di esami prenotati
        JScrollPane scrollPane2 = new JScrollPane(examListPren);
        scrollPane2.setBorder( new TitledBorder(null, "Elenco esami prenotati, seleziona e premi START per iniziare", TitledBorder.LEADING, TitledBorder.TOP, font, null));

        //aggiunta delle JScrollPane al pannello per le infomazioni infoPanel
        infoPanel.add(scrollPane1);
        infoPanel.add(scrollPane2);

        //BOTTONI PER IL PANNELLO DI CONTROLLO
        //aggiunta bottoni al pannello di controllo controlPanel

        //inserimento del bottone di riconoscimento per inserire matricola e codice fiscale
        JButton login = new JButton("Riconoscimento");
        login.setFont(font);
        login.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(login) ) {
                    //il comando di inseirimento delle credenziali è affidato all'oggetto CommandCredenziali che verifica la validità dei campi
                    new CommandCredenziali(student).execute();
                }
            }
        });
        controlPanel.add(login);

        //inserimento del bottone per la visualizzazione degli esami disponibili
        JButton carica = new JButton("Visualizza Esami");
        carica.setFont(font);
        carica.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(carica) ) {
                    //il comando per la visualizzazione degli esami è affidato all'oggetto CommandCaricaEsami
                    //che si occupa di interagire con il server e aggiornare la lista (dunque automaticamente il modello)
                    //degli esami disponibili
                    new CommandCaricaEsami(student, esami_disponibili).execute();
                }
            }
        });
        controlPanel.add(carica);

        //inserimento del bottone per la prenotazione di un esame
        JButton prenota = new JButton("Prenota");
        prenota.setFont(font);
        prenota.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(prenota) ) {
                    if( student.getMatricola()==0 || student.getCf()==null ){
                        //si verifica che sia avvenuta la fase di riconoscimento
                        JOptionPane.showMessageDialog(null, "Eseguire la fase preliminare di riconoscimento", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if(examListDisp.getSelectedValue()==null){
                            JOptionPane.showMessageDialog(null, "Nessun esame selezionato");
                        } else{
                            //se sono state correttamente inserite le credenziali e si è selezionato almeno un esame tra quelli disponibili
                            //si procede con la prenotazione che è affidata all'oggetto CommandAggiornaPrenotazioni
                            //dopo la prenotazione l'elemento viene aggiunto alla lista degli esami prenotati e rimposso da quella degli esami disponibili
                            new CommandAggiornaPrenotazioni(student, esami_prenotati, examListDisp.getSelectedValue()).execute();
                            esami_disponibili.removeElement(examListDisp.getSelectedValue());
                        }
                    }
                }
            }
        });
        controlPanel.add(prenota);

        //inserimento del bottone per iniziare un esame selezionato
        JButton start = new JButton("Start");
        start.setFont(font);
        start.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(start) ) {
                    if( esami_prenotati.isEmpty() ){
                        //si verifica la presenza di almeno un esame prenotato
                        JOptionPane.showMessageDialog(null, "Prenotare almeno un esame");
                    } else if( examListPren.getSelectedValue()==null ){
                        //si verifica che sia stato selezionato almeno un esame
                        JOptionPane.showMessageDialog(null, "Selezionare un esame");
                    } else {
                        //si verifica che l'esame sia accessibile dunque sia iniziato invocando il metodo iniziaEsame di student
                        //che dietro le quinte utilizza gRPC per ottenere tale informazione dal server
                        boolean statoEsame = student.iniziaEsame(examListPren.getSelectedValue().getID());
                        if( statoEsame==false ){
                            //esame non ancora iniziato
                            JOptionPane.showMessageDialog(null, "Esame non ancora iniziato! Attendere l'orario indicato");
                        } else {
                            //esame iniziato per cui si può procedere con la compilazione
                            JOptionPane.showMessageDialog(null, "Esame iniziato! Premere OK per accedervi");
                            //viene impostato l'esame iniziato come esame corrente
                            student.setEsameCorrente(examListPren.getSelectedValue());
                            //l'esame è rimosso dalla lista dei prenotati
                            esami_prenotati.removeElement(examListPren.getSelectedValue());
                            //si inizializza un oggetto ExamController che viene passato come parametro al costruttore di CommandSvolgiEsame
                            //l'oggetto comando si occupa dello svolgimento dell'esame
                            ExamController examController = new ExamController(student);
                            new CommandSvolgiEsame(student, parent, examController).execute();
                        }
                    }
                }
            }
        });
        controlPanel.add(start);


        //i due pannelli, infoPanel e controlPanel vengono aggiunti a this ovvero al controller
        this.setLayout(new GridLayout(1,1));
        this.add(infoPanel);
        this.add(controlPanel);

    }//buildPanel

}//StudentController

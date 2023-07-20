package controller;

import commands.CommandCaricaEsami;
import model.exam.Question;
import model.exam.QuestionStateClient;
import org.example.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamController extends JPanel {
    private Student student;
    private JPanel visualizeExam;
    //pannello per visualizzare le domande d'esame
    private JPanel controlExam;
    //pannello per inserire i cbottoni di controllo
    private ArrayList<JCheckBox> current_options;
    //lista di oggetto JCheckBox che corrispondono alle possibili opzioni di risposta
    private JLabel current_question;
    //JLabel che visualizza, come testo, la domanda corrente
    private Font font = new Font("Century Gothic", Font.PLAIN, 13);
    private QuestionStateClient question;
    //oggetto per gestiore lo stato della domanda corrente

    public ExamController(Student student){
        this.student=student;
        this.current_options = new ArrayList<>();
        buildPanel();
    }//costruttore

    private void buildPanel(){

        this.setLayout(new GridLayout(1,1));

        //pannello per visualizzare le domande d'esame
        visualizeExam = new JPanel();
        visualizeExam.setLayout(new GridLayout(13, 1));
        visualizeExam.setBorder(new TitledBorder(null, "Seleziona la risposta", TitledBorder.LEADING, TitledBorder.TOP, font, null));
        JLabel info1 = new JLabel("Premere COMFERMA per confermare la propria risposta\n");
        info1.setFont(font);
        JLabel info2 = new JLabel("Premere NEXT per passare alla domanda successiva\n");
        info2.setFont(font);

        visualizeExam.add(info1);
        visualizeExam.add(info2);

        //inizializzazione e inserimento degli oggetti per la visualizzazione della domanda nel pannello visualizeExam
        //per la selezione delle opzioni si utilizzano dei JCheckBox
        this.current_question = new JLabel("Premere NEXT per visualizzare la prima domanda");
        visualizeExam.add(current_question);

        JCheckBox option1 = new JCheckBox("");
        option1.setFont(font);
        visualizeExam.add(option1);

        JCheckBox option2 = new JCheckBox("");
        option2.setFont(font);
        visualizeExam.add(option2);

        JCheckBox option3 = new JCheckBox("");
        option3.setFont(font);
        visualizeExam.add(option3);

        current_options.add(option1);
        current_options.add(option2);
        current_options.add(option3);



        //pannello di controllo con i bottoni per l'interazione
        controlExam = new JPanel();
        controlExam.setLayout(new GridLayout(13, 1));
        controlExam.setBorder(new TitledBorder(null, "Pannello di comando", TitledBorder.LEADING, TitledBorder.TOP, font, null));

        //specifica del JButton next per visualizzare la prossima domanda
        JButton next = new JButton("NEXT");
        next.setFont(font);
        next.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(next) ) {
                    //la domanda corrente aggiornata con la domanda restituita dal metodo prossimaDomanda() di student
                    question = student.prossimaDomanda();
                    if( question==null ){
                        //se la domanda corrente è null allora sono finite le domande e si può procedere con la conferma e la sottomissione dell'esame
                        JOptionPane.showMessageDialog(null, "Domande terminate, premi CONFERMA per sottomettere l'esame");
                    } else {
                        //se non si è giunti all'ultima domanda si deselezionano eventuali risposte precedenti e si modificano i campi di testo
                        //degli oggetti che visualizzano domande e opzioni
                        for( JCheckBox check : current_options ){
                            check.setSelected(false);
                        }
                        current_question.setText(question.getQuestion());
                        current_options.get(0).setText(question.getOption(0));
                        current_options.get(1).setText(question.getOption(1));
                        current_options.get(2).setText(question.getOption(2));
                        //nel momento in cui viene settata opportunamente la grafica, si aziona il timer di 5 minuti relativo alla domanda
                        question.start();
                    }
                }
            }
        });

        //specifica del JButton visualizza risultato per visualizzare il punteggio dell'esame dopo averlo confermato
        JButton risultato = new JButton("VISUALIZZA RISULTATO");
        risultato.setFont(font);
        //fino a che non vengono sottomesse tutte le domande il bottone è disabilitato
        risultato.setEnabled(false);
        risultato.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(risultato) ) {
                    //per ottenere il risultato si utilizza il metodo ottieniRisultato di student che interagisce con il server
                    //per calcolare il risultato sulla base delle risposte date in fase di svolgimento
                    int risultato = student.ottieniRisultato();
                    //il risultato è visualizzato in uno JOptionPane
                    JOptionPane.showMessageDialog(null, "Il risultato del tuo esame è: " + risultato);
                }
            }
        });

        //specifica del JButton visualizza risposte per visualizzare le risposte corrette dell'esame dopo averlo confermato
        JButton risposte = new JButton("VISUALIZZA RISPOSTE CORRETTE");
        risposte.setFont(font);
        //fino a che non vengono sottomesse tutte le domande il bottone è disabilitato
        risposte.setEnabled(false);
        risposte.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(risposte) ) {
                    //nel momento in cui il bottone è premuto viene visualizzata un'apposita finestra nella quale è contenuta
                    //una JTextArea che contiene l'elenco delle domande con le relative risposte esatte
                    //le risposte esatte sono contenute in una Map<String, String> che è ottenuta tramite l'invocazione del metodo risposteEsatte
                    //su student: la mappa con le risposte esatte viene inviata direttamente dal server tramite gRPC
                    JFrame finestra = new JFrame();
                    finestra.setTitle("Risposte esatte");
                    finestra.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
                    finestra.setSize(300, 500);
                    finestra.setLocationRelativeTo(null);
                    JTextArea area = new JTextArea();
                    area.setFont(font);
                    area.setBorder(new TitledBorder(null, "Risposte Corrette", TitledBorder.LEADING, TitledBorder.TOP, font, Color.red));
                    Map<String, String> risposte = student.risposteEsatte();
                    for( String domanda: risposte.keySet() ){
                        area.append("Domanda: " + domanda + "\n");
                        area.append("Risposta: " + risposte.get(domanda) + "\n");
                        area.append("\n");
                    }
                    finestra.add(area);
                    finestra.pack();
                    finestra.setVisible(true);
                }
            }
        });

        //specifica del JButton per la conferma di ciascuna risposta inserita
        JButton conferma = new JButton("CONFERMA");
        conferma.setFont(font);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(conferma) ) {
                    if( student.ultimaDomanda() ){
                        //nel caso in cui la risposta corrente corrisponda all'ultima domanda (verifica fatta tramite il metodo ultimaDomanda
                        //viene visualizzato un messaggio di conclusione dell'esame, il pannello per la visualizzazione dell'esame viene svuotato
                        //e vengono abilitati i bottoni per visualizzare punteggio e risposte
                        JOptionPane.showMessageDialog(null, "Esame completato!\n" +
                                "Premere RISULTATO per visualizzare il tuo punteggio.\n" +
                                "Premere RISPOSTE per conoscere le risposte esatte.");
                        visualizeExam.removeAll();
                        visualizeExam.add(new JLabel("ESAME COMPLETATO!"));
                        visualizeExam.repaint();
                        risultato.setEnabled(true);
                        risposte.setEnabled(true);
                    } else {
                        //se non si è all'ultima domanda si verifica preliminarmente lo stato della domanda:
                        //se è trascorso il tempo stabilito per la consegna, non è più possibile rispondere e si esorta a passare alla prossima domanda
                        if( question.getActive()==false ){
                            JOptionPane.showMessageDialog(null, "Non è più possibile rispondere alla domanda, premere NEXT e passare alla prossima", "Domanda Scaduta", JOptionPane.ERROR_MESSAGE);
                        } else {
                            //se la domanda non è scaduta si controlla che sia selezionata una sola risposta e si procede inviando tale risposta al server
                            int risposteSelezionate=0;
                            int risposta=-1;
                            for(int i=0; i<current_options.size(); i++ ) {
                                if( current_options.get(i).isSelected() ) {
                                    risposteSelezionate++;
                                    if( risposteSelezionate>1) {
                                        JOptionPane.showMessageDialog(null, "Selezionare una sola risposta");
                                        break;
                                    }
                                    risposta = (i);
                                }
                            }
                            if( risposteSelezionate==1 ){
                                //se è stata selezionata una sola risposta lo stato della domanda cambia (in PRESENTATA)
                                //e si procede inviando la risposta al sever tramite il metodo inviaRisposta
                                //il metodo restituisce un booleano che è true se la risposta è stata processata mentre false altrimenti
                                //viene restituito false nel caso in cui sia scaduto il tempo per sottomettere l'esame
                                question.submit();
                                boolean processata = student.inviaRisposta(risposta);
                                if( processata==false ) {
                                    //se scaduto il tempo per la sottomissione dell'esame viene data la possibilità di visualizzare punteggio e risposte
                                    JOptionPane.showMessageDialog(null, "Il tempo a tua disposizione per completare l'esame è terminato\n" +
                                                    "Premere RISULTATO per visualizzare il tuo punteggio.\n" +
                                                    "Premere RISPOSTE per conoscere le risposte esatte.",
                                            "Tempo scaduto", JOptionPane.ERROR_MESSAGE);

                                    risultato.setEnabled(true);
                                    risposte.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            }
        });

        //aggiunta di tutti i bottoni di controllo al pannello controlPanel
        controlExam.add(next);
        controlExam.add(conferma);
        controlExam.add(risultato);
        controlExam.add(risposte);

        //aggiunta dei pannelli di visualizzazione e controllo a this
        this.add(visualizeExam);
        this.add(controlExam);
    }//buildPanel

}//ExamController

package commands;

import org.example.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CommandCredenziali implements Command {
    private Student student;
    private JTextField matricolaInput;
    private JTextField cfInput;
    private JButton invio;
    private Font font = new Font("Century Gothic", Font.PLAIN, 13);

    public CommandCredenziali(Student student){
        this.student=student;
    }

    @Override
    public void execute() {
        //creazione della finestra di dialogo per inserire i campi
        JFrame frame = new JFrame();
        frame.setTitle("Inserisci i tuoi dati");
        frame.setSize(500, 110);
        frame.setLocationRelativeTo(null);

        //alla chiusura della finestra vengono ripuliti i campi che altrimenti rimarrebbero compilati
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                matricolaInput.setText("");
                cfInput.setText("");
                frame.setVisible(false);
            }
        });//windowClosing
        JPanel pannello = new JPanel();
        pannello.setLayout(new GridLayout(3, 2));

        //campo per l'inserimento della matricola
        JLabel matricola = new JLabel("Inserisci la tua matricola", JLabel.HORIZONTAL);
        matricola.setFont(font);
        pannello.add(matricola);
        matricolaInput = new JTextField("", 13);
        matricolaInput.setFont(font);
        pannello.add(matricolaInput);

        //campo per l'inserimento del codice fiscale
        JLabel cf = new JLabel("Inserisci il tuo codice fiscale", JLabel.HORIZONTAL);
        cf.setFont(font);
        pannello.add(cf);
        cfInput = new JTextField("", 13);
        cfInput.setFont(font);
        pannello.add(cfInput);

        //JButton per la conferma dei dati
        invio = new JButton("Invia Dati");
        invio.setFont(font);
        pannello.add(invio);
        invio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //definizione delle regex
                    String regexCF = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
                    String regexMatricola = "^\\d{6}$";
                    int matricolaInserita = Integer.parseInt(matricolaInput.getText());
                    String cfInserito = cfInput.getText().toUpperCase();
                    //verifica che i dati inseriti siano correttamente formattati
                    if( matricolaInput.getText().matches(regexMatricola) && cfInserito.matches(regexCF)){
                        //se i dati sono corretti a livello sintattico si procede con il settaggio
                        //viene visualizzato un messaggio che conferma l'inserimento dei dati e la finestra viene chiusa
                        student.setCf(cfInserito);
                        student.setMatricola(matricolaInserita);
                        JOptionPane.showMessageDialog(null, "Dati correttamente inseriti, procedi con la prenotazione");
                        frame.setVisible(false);
                    } else {
                        //altrimenti, se i dati non sono corretti viene visualizzato un messaggio diagnostico e si può procedere con
                        //un nuovo inserimento
                        JOptionPane.showMessageDialog(null, "Inserire una matricola e un codice fiscale validi");
                    }

                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Dati non inseriti correttamente", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(pannello);
        frame.setVisible(true);
    }//execute

}//CommandCredenziali

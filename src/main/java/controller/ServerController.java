package controller;

import model.exam.ExamIF;
import model.exam.MultipleChoiceExam;

import model.exam.builder.ExamParser;
import model.exam.MultipleChoiceExamBuilder;
import org.example.Server;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class ServerController extends JPanel {
    private Server server;
    private int progressiveID=0;
    //variabile necessaria per fornire un identificativo a ciascun esame inserito nel sistema
    private JPanel controlPanel;
    //pannello di controllo fa inserire nel pannello più generale: all'interno del pannello di controllo sono inseriti
    //bottoni e strumenti necessari all'inserimento di un esame
    private JFrame parent;
    //variabile che mantiene un riferimento alla finestra principale all'interno della quale è aggiunto il pannello
    private Font font = new Font("Century Gothic", Font.PLAIN, 16);
    private Font font2 = new Font("Century Gothic", Font.PLAIN, 13);
    private static final long QTIME = 300000; //cinque minuti x domanda

    public ServerController(Server server, JFrame frame){
        this.server=server;
        this.parent=frame;
        server.setServerController(this);
        buildPanel();
    }//costruttore

    private void buildPanel(){
        //metodo che costruisce effettivamente il pannello

        //creazione del pannello di controllo e aggiunta di dettagli
        //il Layout del pannello è un GridBagLayout che permette di posizionare i vari Component in una grigli specificando anche il 'peso' di ciascun elemento
        controlPanel = new JPanel();
        controlPanel.setBorder( new TitledBorder(null, "Inserisci Esame", TitledBorder.LEADING, TitledBorder.TOP, font));
        GridBagLayout gbl_panel = new GridBagLayout();
        controlPanel.setLayout( gbl_panel );
        gbl_panel.columnWidths = new int[]{180, 40};
        gbl_panel.columnWeights = new double[]{0.0, 0.0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};

        //inserimento di un testo per istruzioni
        JLabel toolTip = new JLabel("Utilizza i comandi per inserire un esame");
        toolTip.setFont(font2);
        GridBagConstraints gbc_toolTip = new GridBagConstraints();
        gbc_toolTip.gridwidth = 5;
        gbc_toolTip.insets = new Insets(0, 0, 5, 5);
        gbc_toolTip.gridx = 0;
        gbc_toolTip.gridy = 0;
        controlPanel.add(toolTip, gbc_toolTip);

        //inserimento di un JTextFiel che visualizza il path del file da cui prelevare l'esame
        JTextField filePath = new JTextField("scegli");
        filePath.setColumns(50);
        filePath.setHorizontalAlignment(SwingConstants.LEFT);
        filePath.setFont(font2);
        filePath.setEnabled(false);
        GridBagConstraints gbc_filePath = new GridBagConstraints();
        gbc_filePath.fill = GridBagConstraints.HORIZONTAL;
        gbc_filePath.gridwidth = 5;
        gbc_filePath.insets = new Insets(0, 0, 5, 5);
        gbc_filePath.gridx = 0;
        gbc_filePath.gridy = 1;
        controlPanel.add(filePath, gbc_filePath);


        //inserimento del JButton che apre un FileChooser che permette di navigare nelle directory per selezionare il file
        JButton apri = new JButton("apri");
        apri.setFont(font2);
        apri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(apri) ) {
                    JFileChooser chooser=new JFileChooser();
                    try{
                        if( chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
                            if( !chooser.getSelectedFile().exists() ){
                                JOptionPane.showMessageDialog(null,"File inesistente!");
                            }
                            else{
                                File fileDiSalvataggio=chooser.getSelectedFile();
                                filePath.setText( fileDiSalvataggio.getAbsolutePath() );
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null,"Nessuna apertura!");
                    }catch( Exception exc ){
                        exc.printStackTrace();
                    }
                }
            }
        });
        GridBagConstraints gbc_apri = new GridBagConstraints();
        gbc_apri.insets = new Insets(0, 0, 5, 0);
        gbc_apri.anchor = GridBagConstraints.NORTHEAST;
        gbc_apri.gridx = 5;
        gbc_apri.gridy = 1;
        controlPanel.add(apri, gbc_apri);

        //inserimento di un'indicazione JLabel per indicare la data dell'esame
        JLabel data = new JLabel("Inserire la data dell'esame");
        data.setFont(font2);
        GridBagConstraints gbc_data = new GridBagConstraints();
        gbc_data.anchor = GridBagConstraints.EAST;
        gbc_data.insets = new Insets(0, 0, 5, 5);
        gbc_data.gridx = 0;
        gbc_data.gridy = 5;
        controlPanel.add(data, gbc_data);

        //inserimento del JComboBox per la selezione del giorno
        String [] days = new String[32];
        days[0]="giorno";
        for( int i=1; i<days.length; i++ ){
            days[i]=Integer.toString(i);
        }
        JComboBox giorno = new JComboBox(days);
        GridBagConstraints gbc_giorno = new GridBagConstraints();
        gbc_giorno.anchor = GridBagConstraints.EAST;
        gbc_giorno.insets = new Insets(0, 0, 5, 5);
        gbc_giorno.gridx = 1;
        gbc_giorno.gridy = 5;
        controlPanel.add(giorno, gbc_giorno);

        //inserimento del JComboBox per la selezione del mese
        String [] months = new String[13];
        months[0]="mese";
        for( int i=1; i<months.length; i++ ){
            months[i]=Integer.toString(i);
        }
        JComboBox mese = new JComboBox(months);
        GridBagConstraints gbc_mese = new GridBagConstraints();
        gbc_mese.anchor = GridBagConstraints.EAST;
        gbc_mese.insets = new Insets(0, 0, 5, 5);
        gbc_mese.gridx = 2;
        gbc_mese.gridy = 5;
        controlPanel.add(mese, gbc_mese);

        //inserimento del JComboBox per la selezione dell'anno (indicato solo anno corrente 2023)
        String [] years = {"anno","2023"};
        JComboBox anno = new JComboBox(years);
        GridBagConstraints gbc_anno= new GridBagConstraints();
        gbc_anno.anchor = GridBagConstraints.EAST;
        gbc_anno.insets = new Insets(0, 0, 5, 5);
        gbc_anno.gridx = 3;
        gbc_anno.gridy = 5;
        controlPanel.add(anno, gbc_anno);

        //inserimento di un'indicazione JLabel per indicare l'orario dell'esame
        JLabel orario = new JLabel("Inserire l'ora di inizio");
        orario.setFont(font2);
        GridBagConstraints gbc_orario = new GridBagConstraints();
        gbc_orario.anchor = GridBagConstraints.EAST;
        gbc_orario.insets = new Insets(0, 0, 5, 5);
        gbc_orario.gridx = 0;
        gbc_orario.gridy = 6;
        controlPanel.add(orario, gbc_orario);

        //inserimento del JComboBox per la selezione dell'ora
        String [] hours = new String[14];
        hours[0]="ora";
        for( int i=1; i<hours.length-1; i++ ){
            hours[i]=Integer.toString(i+7);
        }
        hours[13]="23";
        JComboBox ora = new JComboBox(hours);
        GridBagConstraints gbc_ora = new GridBagConstraints();
        gbc_ora.anchor = GridBagConstraints.EAST;
        gbc_ora.insets = new Insets(0, 0, 5, 5);
        gbc_ora.gridx = 1;
        gbc_ora.gridy = 6;
        controlPanel.add(ora, gbc_ora);

        //inserimento del JComboBox per la selezione del minuto specifico
        String [] minutes = new String[61];
        minutes[0] = "minuti";
        for( int i=0; i<minutes.length-1; i++ ){
            if( i<10 )
                minutes[i+1] = "0"+i;
            else
                minutes[i+1] = Integer.toString(i);
        }
        JComboBox minuto = new JComboBox(minutes);
        GridBagConstraints gbc_minuto = new GridBagConstraints();
        gbc_minuto.anchor = GridBagConstraints.EAST;
        gbc_minuto.insets = new Insets(0, 0, 5, 5);
        gbc_minuto.gridx = 2;
        gbc_minuto.gridy = 6;
        controlPanel.add(minuto, gbc_minuto);

        //inserimento di un'indicazione JLabel per indicare la materia dell'esame
        JLabel type = new JLabel("Inserire la materia dell'esame");
        type.setFont(font2);
        GridBagConstraints gbc_type = new GridBagConstraints();
        gbc_type.anchor = GridBagConstraints.EAST;
        gbc_type.insets = new Insets(0, 0, 5, 5);
        gbc_type.gridx = 0;
        gbc_type.gridy = 7;
        controlPanel.add(type, gbc_type);

        //inserimento del JTextFiel in cui indicare la materia
        JTextField subject = new JTextField();
        subject.setFont(font2);
        GridBagConstraints gbc_subject = new GridBagConstraints();
        gbc_subject.anchor = GridBagConstraints.EAST;
        gbc_subject.insets = new Insets(0, 0, 5, 5);
        gbc_subject.gridwidth = 3;
        gbc_subject.gridx = 1;
        gbc_subject.gridy = 7;
        subject.setColumns(15);
        controlPanel.add(subject, gbc_subject);

        //inserimento di una JTextAre al pannello in cui vengono visualizzati gli esami inseriti
        JTextArea textArea = new JTextArea();
        Font font = new Font("Century Gothic", Font.PLAIN, 13);
        textArea.setBorder(new TitledBorder( null, "Esami Inseriti", TitledBorder.LEADING, TitledBorder.TOP, font, null ));
        textArea.setEditable(false);
        textArea.setFont(font);

        //inserimento del bottone di conferma per procedere con il caricamento dell'esame nel server
        //con le informazioni indicate
        JButton conferma = new JButton("CONFERMA");
        conferma.setFont(font2);
        conferma.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                JButton pressed = (JButton)event.getSource();
                if( pressed.equals(conferma) ) {
                    //vengono prelevati i dati per comporre la data
                    int day = Integer.parseInt(days[giorno.getSelectedIndex()]);
                    int month = Integer.parseInt(months[mese.getSelectedIndex() - 1]);
                    int year = Integer.parseInt(years[anno.getSelectedIndex()]);
                    int hour = Integer.parseInt(hours[ora.getSelectedIndex()]);
                    int minute = Integer.parseInt(minutes[minuto.getSelectedIndex()]);
                    Date exam_date = new Date(year - 1900, month, day, hour, minute);
                    Date now = new Date(System.currentTimeMillis());
                    //verifica della validità della data inserita: i parametri della data non devono essere maggiori dei parametri dell'ora/giorno corrente
                    if (exam_date.before(now)) {
                        JOptionPane.showMessageDialog(parent, "Inserire una data valida!", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //verifica del corretto inserimento del path dell'esame
                        if (filePath.getText().equals("scegli")){
                            JOptionPane.showMessageDialog(parent, "Indicare un percorso di ricerca per l'esame da inserire", "Errore", JOptionPane.ERROR_MESSAGE);
                        } else {
                            //se il percorso è specificato si procede analizzando il file e costruendo un esame con il MultipleChoiceExamBuilder
                            String path = filePath.getText();
                            MultipleChoiceExam esame = new MultipleChoiceExam(exam_date);
                            MultipleChoiceExamBuilder builder = new MultipleChoiceExamBuilder(esame);
                            ExamParser parser = new ExamParser(builder, path);
                            parser.build();
                            //dopo la costruzione dell'esame si settano il tipo di esame, l'id dell'esame e la dutata e l'esame viene inserito nel server
                            //come durata dell'esame viene moltiplicato il numero di domande * 5 minuti
                            builder.getExam().setType(subject.getText());
                            builder.getExam().setID(progressiveID);
                            long durata = builder.getExam().getQuestionsNumber() * QTIME;
                            builder.getExam().setTTL(durata);
                            server.addExam(progressiveID, builder.getExam());
                            progressiveID++;
                            //viene visualizzato un messaggio che notifica il corretto inserimento
                            //l'esame viene visualizzato nell JTextArea e vengono ripuliti i campi di inserimento
                            JOptionPane.showMessageDialog(parent, "Esame inserito correttamente");
                            textArea.append(builder.getExam().toString() + "\n");
                            filePath.setText("scegli");
                            subject.setText("");
                        }
                    }
                }
            }
        });
        GridBagConstraints gbc_conferma = new GridBagConstraints();
        gbc_conferma.insets = new Insets(0, 0, 5, 0);
        gbc_conferma.gridx = 5;
        gbc_conferma.gridy = 12;
        controlPanel.add(conferma, gbc_conferma);


        //inserimento del pannello di contorllo, controlPanel e della JTextArea nel pannello ServerController
        setLayout(new GridLayout(2,1));
        add(new JScrollPane(textArea));
        add(controlPanel);
    }//buildPanel

}//ServerController

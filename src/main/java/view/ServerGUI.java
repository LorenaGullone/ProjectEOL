package view;

import controller.ServerController;
import io.grpc.ServerBuilder;
import org.example.Server;
import servicesImpl.ServerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class ServerGUI {
    private static boolean consensoUscita(){
        int option=JOptionPane.showConfirmDialog( null, "Continuare? ","Uscendo si perderanno tutti i dati!", JOptionPane.YES_NO_OPTION);
            return option==JOptionPane.YES_OPTION;
    }//consensoUscita

    public static void main( String [] args ) throws IOException, InterruptedException {
        Server server = new Server();
        io.grpc.Server serverService = ServerBuilder.forPort(9990).addService(new ServerImpl(server)).build();
        serverService.start();
        System.out.println("Server attivo sulla porta " + serverService.getPort());

        JFrame frame = new JFrame();
        ServerController serverController= new ServerController(server, frame);


        frame.setTitle("University Server");
        frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if( consensoUscita() ) System.exit(0);
            }
        } );

        frame.add(serverController);

        frame.pack();
        frame.setSize(1000,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(1, 1));
        frame.setVisible(true);

        serverService.awaitTermination();
    }//main
}//ServerGUI

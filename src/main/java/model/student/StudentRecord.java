package model.student;

import model.exam.ExamEvent;
import model.exam.ExamListener;

import java.net.InetAddress;

public class StudentRecord {
    private int matricola;
    private String cf;
    private InetAddress address;
    private int port;

    public StudentRecord(int matricola, String cf, InetAddress address, int port){
        this.matricola=matricola;
        this.cf=cf;
        this.address=address;
        this.port=port;
    }

    public String toString(){
        return matricola + " " + cf;
    }

    //METODI GETTERS AND SETTERS
    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}//StudentRecord

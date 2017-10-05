package client;

import gui.IDataReady;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

    Socket clientSocket;
    private Scanner input;
    private PrintWriter output;
    private IDataReady observer;
    private boolean keepRunning = true;

    public void addObserver(IDataReady observer) {
        this.observer = observer;
    }

    public void closeConnection() {
        send("QUIT");
        keepRunning = false;
    }

    public void connect(String address, int port) throws IOException {
        clientSocket = new Socket(address, port);
        input = new Scanner(clientSocket.getInputStream());
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.start();
    }

    public void send(String msg) {
        output.println(msg);
    }
    
    public void login(String username) {
        String login = "LOGIN:" + username;
        output.println(login);
    } 

    @Override
    public void run() {
        while (keepRunning) {
            String msg = input.nextLine();
            observer.messegaeReady(msg);
        }
        try {
            clientSocket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

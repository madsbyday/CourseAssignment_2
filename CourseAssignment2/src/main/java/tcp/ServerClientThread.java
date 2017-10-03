/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alexander
 */
public class ServerClientThread extends Thread {

    public Socket clientSocket;
    
    Server server;
    PrintWriter toClient;
    BufferedReader fromClient;
    String username;
    
    public ServerClientThread(Socket s, Server server) throws IOException {
        clientSocket = s;
        this.server = server;
        toClient = new PrintWriter(clientSocket.getOutputStream(), true);
        fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void run() {
        System.out.println("Server log: Client connected...");

        try {
            String inputLine;;

            while ((inputLine = fromClient.readLine()) != null) {

                String[] parts = inputLine.split(":");
                String token = parts[0];
                if (token.equals("LOGIN")) {
                     username = parts[1];
                     server.addHandler(username, this);
                }
            }

            toClient.close();
            fromClient.close();
            clientSocket.close();

        } 
        catch (Exception e) {
            System.out.println("Server log: Problem with Communication Server...");
        }
    }
    public void send(String msg) {
        toClient.println(msg);
    }

}

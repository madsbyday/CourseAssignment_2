/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.BufferedReader;
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

    public ServerClientThread(Socket s) {
        clientSocket = s;
        start();
    }

    public void run() {
        System.out.println("Server log: Client connected...");

        try {
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            toClient.println("WELCOME...");

            String inputLine;

            String result = "";

            boolean login = false;
            boolean logout = false;

            while ((inputLine = fromClient.readLine()) != null) {
                
                if (inputLine.contains(":")) {
                String cmd = inputLine.substring(0, inputLine.indexOf(":"));
                switch (cmd) {
                    case "LOGIN":
                        // Add username
                        if (!login) {
                            String name = (inputLine.substring(inputLine.indexOf(":") + 1)).replace(" ", "");
                            result = name;
                            login = true;
                        } else {
                            result = "You are allready loged in";
                        }
                        break;
                    case "MSG":
                        // 
                        if (!login) {
                            result = "You need to login to message other users";
                        } else {
                            String recivers = (inputLine.substring(inputLine.indexOf(":") + 1, inputLine.lastIndexOf(":"))).replace(" ", "");
                            String message = inputLine.substring(inputLine.lastIndexOf(":") + 1);
                            if (message.charAt(0) == ' ') {
                                message = message.substring(1);
                            }
                            result = "To: " + recivers + ": " + message;
                        }
                        break;
                    case "LOGOUT":
                        // Log user out
                        if (!login) {
                            result = "You need to login to message other users";
                        } else {
                            result = "User loged out";
                            logout = true;
                        }
                        break;
                    case "HELP":
                        result = "LOGIN:<name> | MSG<recivers>:<message> | LOGOUT:";
                    default:
                        result = "Command not found - type 'HELP: to see all commands";
                }
                }
                else {
                    result = "Command not found - type 'HELP:' to see all commands";
                }
                
                toClient.println(result);

                if (logout) {
                    break;
                }
            }

            toClient.close();
            fromClient.close();
            clientSocket.close();

        } catch (Exception e) {
            System.out.println("Server log: Problem with Communication Server...");
        }
    }

}

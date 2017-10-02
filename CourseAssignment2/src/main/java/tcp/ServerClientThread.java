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
import java.util.Date;

/**
 *
 * @author Alexander
 */
public class ServerClientThread extends Thread{

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

            Date d = new Date();
            String date = d.toString();
            boolean hashtag = false;
            
            while ((inputLine = fromClient.readLine()) != null) {
                
                for (int i = 0; i < inputLine.length(); i++) {
                    if (inputLine.charAt(i) == '#') {
                        hashtag = true;
                        String cmd = inputLine.substring(0, i);
                        switch (cmd) {
                            case "TIME":
                                result = date;
                                break;
                            case "UPPER":
                                result = (inputLine.substring(i + 1)).toUpperCase();
                                break;
                            case "LOWER":
                                result = (inputLine.substring(i + 1)).toLowerCase();
                                break;
                            case "REVERSE":
                                result = new StringBuilder(inputLine.substring(i + 1)).reverse().toString();
                                break;
                            case "TRANSLATE": // Prøv et dyr
                                // <editor-fold defaultstate="collapsed" desc="Swich with tanslatable words">
                                String word = inputLine.substring(i + 1).toLowerCase();
                                switch (word) {
                                    case "hund":
                                        result = "dog";
                                        break;
                                    case "fugl":
                                        result = "bird";
                                        break;
                                    case "fisk":
                                        result = "fish";
                                        break;
                                    case "abe":
                                        result = "ape";
                                        break;
                                    case "kat":
                                        result = "cat";
                                        break;
                                    case "hest":
                                        result = "horse";
                                        break;
                                    case "ko":
                                        result = "cow";
                                        break;
                                    default:
                                        result = "I don't know that animal";
                                }
                                // </editor-fold>
                                break;
                            default:
                                result = "#COMMAND_NOT_FOUND";

                        }
                    } else if (!hashtag) {
                        result = inputLine;
                    }
                }

                toClient.println(result);
                hashtag = false;

                if (inputLine.equals("Bye.")) {
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

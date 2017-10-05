/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.IOException;

/**
 *
 * @author vfgya_000
 */
public class ClientTester {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client c = new Client();
        
        c.addObserver((msg) -> {
            System.out.println("Recieved msg: " + msg);
        });
        c.connect("localhost", 6666);
        c.send("LOGIN:Test");
        c.send("MSG:Test:hej");
        Thread.sleep(100);
        
        c.closeConnection();
        
        System.out.println("Done");
    }
    
}

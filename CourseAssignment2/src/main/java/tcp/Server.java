package tcp;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private Map<String, ServerClientThread> handlers = new HashMap();

    public void addHandler(String user, ServerClientThread sct) {
        handlers.put(user, sct);
        
        String msg = "CLIENTLIST:";
        for (String u : handlers.keySet()) {
            msg += u + ",";
        }
        msg = msg.substring(0, msg.length() - 1);
        for (ServerClientThread h : handlers.values()) {
            h.send(msg);
        }
    }

    public void removeHandler(String username) {
        handlers.remove(username);
        
        String msg = "CLIENTLIST:";
         for (String u : handlers.keySet()) {
            msg += u + ",";
        }
        msg = msg.substring(0, msg.length() - 1);
        for (ServerClientThread h : handlers.values()) {
            h.send(msg);
        }
    }

    public void sendTo(String recivers, String msg, String sender) {
        String result = "MSGRES:" + sender + ":" + msg;
        if (recivers.equals("*")) {
            for (ServerClientThread h : handlers.values()) {
                h.send(result);
            }
        } else {
            String[] names = recivers.split(",");
            for (int i = 0; i < names.length; i++) {

                if (handlers.containsKey(names[i])) {
                    ServerClientThread h = handlers.get(names[i]);
                    h.send(result);
                }
            }
        }
    }

    public void runServer(String ip, int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));

            System.out.println("Server log: Server created...");
            System.out.println("Server log: Listening for clients on port " + port + "...");

            try {
                System.out.println("Server log: Waiting for connections...");

                while (true) {
                    Socket s = serverSocket.accept(); // BLOCKING CALL
                    new ServerClientThread(s, this).start();
                }
            } catch (Exception e) {
                System.err.println("Server log: Accepting connection failed...");
            }
        } catch (Exception e) {
            System.out.println("Server log: Could not listen on port: " + port + "...");
        }
    }

    public static void main(String[] args) {
        new Server().runServer("127.0.0.1", 6666);
    }

}

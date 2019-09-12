package Sockets;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible for reading user's input and send it to the
 * server. It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        Console console = System.console();

        String text = null;

        do {
            // TODO: Input should come from SendMessage method of user class
            try {
                text = console.readLine();
                writer.println(text);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        } while (!text.equals("Bye"));
        this.client.user.Logout();

        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // try {
        // socket.close();
        // } catch (IOException ex) {
        // System.out.println("Error writing to server: " + ex.getMessage());
        // }
    }
}
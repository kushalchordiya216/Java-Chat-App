package Sockets;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

import Profile.User;

public class ChatClient {
    private String hostname;
    private int port;
    private Socket socket;
    User user;

    private ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    private void execute() {
        try {
            this.socket = new Socket(hostname, port);
            this.user = new User();
            Boolean result = false;
            while (!result) {
                System.out.println("1.Login\n2.Register\n");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                switch (choice) {
                case 1:
                    result = this.user.Login();
                    break;
                case 2:
                    result = this.user.Register();
                    break;
                default:
                    System.out.println("enter appropriate choice");
                }
            }
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        if (args.length < 2)
            return;

        ChatClient client = new ChatClient(args[0], Integer.parseInt(args[1]));
        client.execute();
    }

    public Socket getSocket() {
        return socket;
    }
}

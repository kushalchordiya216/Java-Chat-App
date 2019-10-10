package Sockets;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Profile.Message;
import Profile.User;

public class ChatClient {
    private Socket socket;
    private Scanner sc = new Scanner(System.in);
    private ObjectOutputStream writerToServer;
    private ObjectInputStream readFromServer;
    private User user;

    private ChatClient(String hostname, int port) {
        try {
            this.user = new User();
            this.socket = new Socket(hostname, port);

            this.writerToServer = new ObjectOutputStream(socket.getOutputStream());
            this.readFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void TerminateConnection() {
        System.out.println("Logging out....GoodBye!");
    }

    private void writeMessages() {
        new Thread(() -> {
            String content = null;
            do {
                try {
                    content = sc.nextLine();
                    Message message = new Message(content, user.getId(), user.getUsername(), user.getActiveChannel());
                    writerToServer.writeObject(message);
                } catch (NullPointerException | IOException ne) {
                    ne.printStackTrace();
                }
            } while (content != "Bye" || content != null);
            TerminateConnection();
        }).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void CreateGroup() {
        System.out.println("Enter Name of new Channel (You will be the admin)");
        String channelName = sc.nextLine();
        System.out.println("Enter a short one line description");
        String description = sc.nextLine();
        System.out.println(
                "Enter list of users you want to add to the channel.\n Type in '@Done' after you've typed all names");
        ArrayList<String> usernames = new ArrayList<String>();
        String username;
        do {
            username = sc.nextLine();
            usernames.add(username);
        } while (!username.equals("@Done"));
        System.out.println("Adding users and creating new group....");
    }

    private void readMessages() {
        new Thread(() -> {
            {
                while (true) {
                    try {
                        Message message = (Message) readFromServer.readObject();
                        System.out.println("[" + message.getSenderName() + "]" + message.getContent());
                    } catch (IOException ex) {
                        System.out.println("Error reading from server: " + ex.getMessage());
                        ex.printStackTrace();
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void fetchChatHistory() {
        String message = "";
        String timestamp = "";
        do {
            try {
                message = (String) this.readFromServer.readObject();
                System.out.println(message);
                // timestamp = (String) this.readFromServer.readObject();
                // System.out.println(timestamp);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } while (!message.equals("@end"));
    }

    private void execute() {
        try {
            Boolean result = false;
            while (!result) {
                String[] Queries = null;
                System.out.println("1.Login\n2.Register\n");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                switch (choice) {
                case 1:
                    Queries = this.user.Login();
                    for (String q : Queries) {
                        this.writerToServer.writeObject(q);
                    }
                    result = (Boolean) readFromServer.readObject();
                    if (!result) {
                        System.out.println("Invalid Credentials!");
                    }
                    break;
                case 2:
                    Queries = this.user.Register();
                    for (String q : Queries) {
                        this.writerToServer.writeObject(q);
                    }
                    result = (Boolean) readFromServer.readObject();
                    if (!result) {
                        System.out.println("Username Already Taken!");
                    }
                    break;
                default:
                    System.out.println("Enter appropriate choice");
                }
                assert Queries != null;
                this.user.setUsername(Queries[0]);
                this.user.setActiveChannel(1);
            }
            System.out.println("Connected to the chat server\nWelcome " + this.user.getUsername());
            fetchChatHistory();
            writeMessages();
            readMessages();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 8080);
        client.execute();
    }

}

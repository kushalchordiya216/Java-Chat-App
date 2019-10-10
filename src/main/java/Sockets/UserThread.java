package Sockets;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.sql.*;
import Profile.User;
import Profile.Message;
import Database.MessagesTable;
import Database.UserTable;

public class UserThread implements Runnable {
    private Socket socket;
    private Server server;

    private ObjectOutputStream writeToClient;
    private ObjectInputStream readFromClient;

    private DBOps dbops = new DBOps();
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    private User user;

    UserThread(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.readFromClient = new ObjectInputStream(socket.getInputStream());
        this.writeToClient = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendChatHistory() {
        try {
            ResultSet rs = dbops.fetchMessage(this.user.getActiveChannel());
            if (rs != null) {
                while (rs.next()) {
                    this.writeToClient.writeObject(rs.getTimestamp("time").toString());
                    this.writeToClient.writeObject(rs.getString("sender_name") + ": " + rs.getString("content"));
                }
            } else {
                this.writeToClient.writeObject("no messages in the chat yet");
            }
            this.writeToClient.writeObject("@end");
        } catch (SQLException | IOException | NullPointerException e) {
            System.out.println("no chat history for " + this.user.getUsername());
        }
    }

    public void run() {
        try {
            do {
                boolean success = false;
                String username = (String) readFromClient.readObject();
                String password = (String) readFromClient.readObject();
                String action = (String) readFromClient.readObject();
                if (action.equals("1")) {
                    success = dbops.verifyUser(new String[] { username, password }, this.writeToClient);
                } else if (action.equals("0")) {
                    success = dbops.createUser(new String[] { username, password }, this.writeToClient);
                }
                if (success) {
                    this.user = new User();
                    user.setActiveChannel(1);
                    user.setUsername(username);
                    user.setPassword(password);
                    this.sendChatHistory();
                    Message clientMessage;
                    do {
                        final Message currentMessage = (Message) readFromClient.readObject();
                        clientMessage = currentMessage;
                        char type = currentMessage.getContent().charAt(0);
                        if (type == '@') {
                            switch (currentMessage.getContent()) {
                            case "@createGroup":
                                // TODO: accept message in the form "name, description, comma separated
                                // usernames"
                                break;
                            case "@switchGroup":
                                // TODO: get active channel from user and change it, call fetch messages after
                                // this
                                break;
                            case "@multimediaMessage":
                                // TODO: read from given URI
                                break;
                            case "@listGroups":
                                // TODO: fetch all groups for users
                                break;
                            }
                        }
                        server.broadcast(currentMessage, this);
                        executor.submit(() -> {
                            dbops.pushMessage(currentMessage);
                        });
                    } while (!clientMessage.getContent().equals("Bye"));
                    socket.close();
                    break;
                }
            } while (this.socket.isConnected());
        } catch (IOException ex) {
            System.out.println("Error in UserThread: User has disconnected ");// + ex.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Problem with broadcasting message");
        }
    }

    /**
     * Sends a message to the client.
     * 
     * @throws IOException
     */
    void sendMessage(Message message) throws IOException {
        this.writeToClient.writeObject(message);
    }
}

class DBOps {
    private MessagesTable messagesTable = new MessagesTable();
    private UserTable userTable = new UserTable();

    Boolean verifyUser(String[] Queries, ObjectOutputStream writer) {
        ResultSet rs = userTable.Retrieve(Queries);
        try {
            if (rs.next() && !rs.getBoolean("loggedIn")) {
                writer.writeObject(true);
                String[] Arr = new String[2];
                Arr[0] = "true";
                Arr[1] = Queries[0];
                userTable.Update(Arr);
                return true;
            } else {
                writer.writeObject(false);
                return false;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    Boolean createUser(String[] Queries, ObjectOutputStream writer) throws IOException {
        int result = userTable.Create(Queries);
        if (result >= 0) {
            writer.writeObject(true);
            return true;
        } else {
            writer.writeObject(false);
            return false;
        }
    }

    void pushMessage(Message message) {
        String[] Queries = { Integer.toString(message.getChannel()), message.getContent(), message.getSenderName() };
        messagesTable.Create(Queries);
    }

    ResultSet fetchMessage(Integer activeChannel) {
        String[] queries = new String[1];
        queries[0] = Integer.toString(activeChannel);
        ResultSet rs = messagesTable.Retrieve(queries);
        try {
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }
}
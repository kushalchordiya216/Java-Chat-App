package Profile;

import Profile.Channels;
import Profile.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import java.util.ArrayList;

public class User {
    private String username, password;
    private Boolean isLoggedIn;
    private ArrayList<Integer> channels;
    private int activeChannel;
    private int id;

    public void setter(ResultSet rs) throws SQLException {
        this.username = rs.getString("name");
        this.password = rs.getString("password");
        this.id = (int) rs.getInt("id");
    }

    public String[] Register() throws SQLException {
        System.out.println("Enter a Username");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter a password");
        String password = sc.nextLine();
        System.out.println("Creating new Account....");
        String[] Queries = { username, password, "0" };
        return Queries;
    }

    public String[] Login() {
        System.out.println("Enter a Username");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter a password");
        String pass = sc.nextLine();
        System.out.println("Logging in ....");
        String[] Queries = { pass, username, "1" };
        return Queries;
    }

    public String[] Logout() {
        String[] Queries = { this.getPassword(), "false", this.getUsername() };
        return Queries;
    }

    public void CreateChannel() {
        // TODO: Take list of users as input and create new group object
    }

    public void SelectChannel() {
        // TODO: Allow user to select one group as the current active group
    }

    public void UpdateChannelList() {
        // TODO: When a user is added to a group update groups ArrayList
    }

    /* getters and setters */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public int getId() {
        return id;
    }

    public void setGroups(ArrayList<Integer> channels) {
        this.channels = channels;
    }

    public void setActiveChannel(int activeChannel) {
        this.activeChannel = activeChannel;
    }

    public int getActiveChannel() {
        return this.activeChannel;
    }

    public void setId(int id) {
        this.id = id;
    }
}
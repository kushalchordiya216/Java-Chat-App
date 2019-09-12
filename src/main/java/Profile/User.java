package Profile;

import Profile.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Database.UserTable;
import java.util.ArrayList;

public class User {
    private String username, password;
    private Boolean isLoggedIn;
    private ArrayList<Long> groups;
    private Group activeGroup;
    private UserTable userTable = new UserTable();

    public Boolean Register() throws SQLException {
        System.out.println("Enter a Username");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter a password");
        String password = sc.nextLine();
        String[] Query = { username, password };
        if (this.userTable.Create(Query)) {
            System.out.println("User account created!");
            this.setPassword(password);
            this.setUsername(username);
            return true;
        } else {
            System.out.println("Username already exists!\nTry logging in directly or use different username");
            return false;
        }
        // TODO: Make a render chat function
    }

    public Boolean Login() {
        System.out.println("Enter a Username");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter a password");
        String pass = sc.nextLine();
        String[] Queries = { pass, username };
        ResultSet rs = this.userTable.Retrieve(Queries);
        try {
            if (rs.next()) {
                if (rs.getBoolean(4)) {
                    System.out.println("User is already logged in from another device");
                    return false;
                }
                this.setUsername(username);
                this.setPassword(pass);
                String[] UpdateQueries = { pass, "true", username };
                userTable.Update(UpdateQueries);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void Logout() {
        String[] Queries = { this.getPassword(), "false", this.getUsername() };
        this.userTable.Update(Queries);
    }

    public String SendMessage(String message) {
        // TODO: Take input written in GUI and pass to writethread
        return null;
    }

    public String ReceiveMessage(String message) {
        // TODO: Take message received by read thread and pass it to GUI
        return null;
    }

    public void CreateGroup() {
        // TODO: Take list of users as input and create new group object
    }

    public void SelectGroup() {
        // TODO: Allow user to select one group as the current active group
    }

    public void UpdateGroupList() {
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

    public ArrayList<Long> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Long> groups) {
        this.groups = groups;
    }

    public Group getActiveGroup() {
        return activeGroup;
    }

    public void setActiveGroup(Group activeGroup) {
        this.activeGroup = activeGroup;
    }
}
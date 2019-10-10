package Profile;

import java.util.ArrayList;
import Profile.User;

public class Channels {
    private int admin;
    private String name;
    private String Description;
    private ArrayList<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
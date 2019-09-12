package Database;

import java.sql.*;

public interface CRUD {

    public String USERNAME = "kushal";
    public String PASSWORD = "#Kushalpasswd123";
    public String URL = "jdbc:mysql://localhost:3306/ChatDB";

    public ResultSet Retrieve(String[] Params);

    public Boolean Create(String[] Params);

    public void Update(String[] Params);

    public Boolean Delete(String[] Params);

}
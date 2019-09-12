package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UserTable implements CRUD {

    private Connection connection;

    public UserTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public ResultSet Retrieve(String[] Parameters) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("SELECT * FROM Users WHERE password=? AND name=?");
            stmt.setString(1, Parameters[0]);
            stmt.setString(2, Parameters[1]);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            System.out.println(meta.getColumnName(meta.getColumnCount()));
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean Create(String[] Parameters) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("INSERT INTO Users(name,password,loggedIn) VALUES(?,?,true)");
            stmt.setString(1, Parameters[0]);
            stmt.setString(2, Parameters[1]);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void Update(String[] Parameters) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("UPDATE Users set password = ?, loggedIn = ? WHERE name = ?");
            stmt.setString(1, Parameters[0]);
            stmt.setBoolean(2, Boolean.parseBoolean(Parameters[1]));
            stmt.setString(3, Parameters[2]);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean Delete(String[] Params) {
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        this.connection.close();
    }

}
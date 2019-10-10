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
                    .prepareStatement("SELECT * FROM Users WHERE name=? AND password=?");
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
    public Integer Create(String[] Parameters) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("INSERT INTO Users(name,password,loggedIn) VALUES(?,?,true)");
            stmt.setString(1, Parameters[0]);
            stmt.setString(2, Parameters[1]);
            stmt.executeUpdate();
            stmt.close();
            ResultSet rs = Retrieve(Parameters);
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void Update(String[] Parameters) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE Users set loggedIn = ? WHERE name = ?");
            stmt.setBoolean(1, Boolean.parseBoolean(Parameters[0]));
            stmt.setString(2, Parameters[1]);
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
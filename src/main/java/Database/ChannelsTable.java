package Database;

import java.sql.*;

public class ChannelsTable implements CRUD {
    private Connection connection;

    public ChannelsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public ResultSet Retrieve(String[] Params) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("SELECT ? FROM Channels WHERE ?=?");
            stmt.setString(1, Params[0]);
            stmt.setString(2, Params[1]);
            stmt.setString(3, Params[2]);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer Create(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("INSERT INTO Channels(name, admin, description) VALUES(?,?,?)");
            stmt.setString(1, Params[0]);
            stmt.setString(2, Params[1]);
            stmt.setString(3, Params[2]);
            stmt.executeUpdate();
            ResultSet rs = Retrieve(Params);
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void Update(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("UPDATE Channels SET name=?, description=? WHERE id=? AND admin =?");
            stmt.setString(1, Params[0]);
            stmt.setString(2, Params[1]);
            stmt.setInt(3, Integer.parseInt(Params[2]));
            stmt.setInt(4, Integer.parseInt(Params[3]));
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean Delete(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("DELETE FROM Channels WHERE name=? AND admin=? AND id=?");
            stmt.setString(1, Params[0]);
            stmt.setString(2, Params[1]);
            stmt.setInt(3, Integer.parseInt(Params[2]));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
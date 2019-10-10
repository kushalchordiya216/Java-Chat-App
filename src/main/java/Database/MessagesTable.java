package Database;

import java.sql.*;
import java.util.Date;

public class MessagesTable implements CRUD {

    private Connection connection;

    public MessagesTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public ResultSet Retrieve(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("SELECT sender_name,content,time FROM Messages WHERE channelId=?");
            stmt.setInt(1, Integer.parseInt(Params[0]));
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
                    .prepareStatement("INSERT INTO Messages(channelId,content,sender_name,time) VALUES(?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(Params[0]));
            stmt.setString(2, Params[1]);
            stmt.setString(3, Params[2]);
            stmt.setTimestamp(4, new Timestamp(new Date().getTime()));
            stmt.executeUpdate();
            ResultSet rs = Retrieve(Params);
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void Update(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("UPDATE Messages SET content=? WHERE sender_name=? and id=?");
            stmt.setString(1, Params[0]);
            stmt.setInt(2, Integer.parseInt(Params[1]));

            int res = stmt.executeUpdate();
            if (res == 0) {
                System.out.println("Update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean Delete(String[] Params) {
        try {
            PreparedStatement stmt = this.connection
                    .prepareStatement("DELETE FROM Messages WHERE sender_name=? AND channelId=? AND id=?");
            stmt.setInt(1, Integer.parseInt(Params[0]));
            stmt.setInt(2, Integer.parseInt(Params[1]));
            stmt.setInt(3, Integer.parseInt(Params[2]));
            int res = stmt.executeUpdate();
            if (res != 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.connection.close();
    }
}

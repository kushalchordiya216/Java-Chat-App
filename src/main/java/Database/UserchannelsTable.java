package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserchannelsTable implements CRUD {
    private Connection connection;

    public UserchannelsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public ResultSet Retrieve(String[] Params) {
        return null;
    }

    @Override
    public Integer Create(String[] Params) {
        return 0;
    }

    @Override
    public void Update(String[] Params) {

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

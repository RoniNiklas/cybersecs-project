package sec.project.dao;

//Roni
import java.sql.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;

@Service
public class AccountDB {

    public AccountDB() {
        initDB();
        if (!contains("make")) {
            save("make", "salasana", "Credit card number: 987654321. Expiration date: 7/11");
        }
        if (!contains("admin")) {
            save("admin", "password", "Credit card number: 123456789. Expiration date: 1/12");
        }
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:file:./database;TRACE_LEVEL_FILE=0;TRACE_LEVEL_SYSTEM_OUT=0", "sa", "");
    }

    public ResultSet getResultSet(Connection c, String query) throws SQLException {
        return c.createStatement().executeQuery(query);
    }

    public void execute(String query) throws SQLException {
        Connection c = openConnection();
        c.createStatement().execute(query);
        c.close();
    }

    private void initDB() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS accounts ("
                    + "username varchar(200) PRIMARY KEY, "
                    + "pw varchar(200), "
                    + "cardData varchar(200)"
                    + ")";
            execute(query);
        } catch (Exception e) {
        }
    }

    public void save(String username, String pw, String cardData) {
        try {
            String query = "INSERT INTO accounts ("
                    + "username, pw, cardData"
                    + ")"
                    + "VALUES ('"
                    + username + "','" + pw + "','" + cardData
                    + "');";
            execute(query);
        } catch (Exception e) {
        }
    }

    public Boolean contains(String username) {
        try {
            Connection c = openConnection();
            String query = "SELECT * FROM accounts "
                    + "WHERE username = '" + username + "';";
            ResultSet rs = getResultSet(c, query);
            if (rs.next()) {
                rs.close();
                c.close();
                return true;
            }
            rs.close();
            c.close();
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public Account getUser(String username) {
        try {
            Connection c = openConnection();
            String query = "SELECT * FROM accounts "
                    + "WHERE username = '" + username + "';";
            ResultSet rs = getResultSet(c, query);
            Account acco = new Account();
            if (rs.next()) {
                acco.setUsername(rs.getString("username"));
                acco.setPw(rs.getString("pw"));
                acco.setCardData(rs.getString("cardData"));
            }
            rs.close();
            c.close();
            return acco;
        } catch (Exception e) {
        }
        return null;
    }

    public boolean checkLogin(String username, String pw) {
        try {
            Connection c = openConnection();
            String query = "SELECT * FROM accounts "
                    + "WHERE username = '" + username + "'"
                    + " AND "
                    + "pw = '" + pw + "';";
            ResultSet rs = getResultSet(c, query);
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }
}

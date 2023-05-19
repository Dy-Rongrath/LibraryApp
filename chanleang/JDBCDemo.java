
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JDBCDemo{
    Connection c;
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
    public JDBCDemo(){

    }

    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            c = (Connection) DriverManager.getConnection(URL, user, password);
            System.out.println("Connected ban hz brooooo");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        return null;
    }
}
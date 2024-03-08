
package Connection;
import java.sql.*;
public class Connexion {

    public Connection postgres() throws Exception
    {
        Connection conne=null;
        try {
            Class.forName("org.postgresql.Driver");
             conne=DriverManager.getConnection("jdbc:postgresql://localhost:5432/testgeneration","postgres","postgres");     
        }    
        catch (Exception e) {
            e.printStackTrace();
        } 
        return conne;
    }
}
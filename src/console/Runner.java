package console;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Runner {

    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        Statement st = null;
        PreparedStatement prSt = null;
        ResultSet res = null;
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        try {
                // 0. Read the properties
                Properties prop = new Properties();
                prop.load(new FileInputStream("db.properties"));
                
                String db_user = prop.getProperty("user");
                String db_pw = prop.getProperty("password");
                String db_url = prop.getProperty("url");

                System.out.println("Connecting to database...");
                System.out.println("Database URL: " + db_url);
                System.out.println("User: " + db_user);

                // 1. Get a connection to database
                conn = DriverManager.getConnection(db_url,prop);
                //conn = DriverManager.getConnection(db_url, db_user, db_password);

                // 2. Statement
                st = conn.createStatement();

                // 3. Execute SQL query
                res = st.executeQuery("select `login`, `password`, `role` from users");

                // 5. Display the result set
                System.out.println("the statement. Output the list of all users:");
                display(res);

                // 2-1. Prepare statement
                prSt = conn.prepareStatement("select `login`, `password`, `role` from users where role = ? ");

                // 3-1. Set the parameters
                prSt.setInt(1, 0);

                // 4-1. Execute SQL query
                res = prSt.executeQuery();

                // 5-1. Display the result set
                System.out.println("the prepared statement:  role = 0 (admin).\nList of users with the role = 'admin':");
                display(res);

                //
                // Reuse the prepared statement: role = 1 (customer)
                //

                // 3-2. Set the parameters
                prSt.setInt(1, 1);

                // 4-2. Execute SQL query
                res = prSt.executeQuery();

                // 5-2. Display the result set
                System.out.println("\nReuse the prepared statement:  role = 1 (customer).\nList of users with the role = 'customer':");
                display(res);

        } catch (Exception exc) {
                exc.printStackTrace();
        } finally {
                if (res != null) {
                        res.close();
                }

                if (st != null) {
                        st.close();
                }

                if (prSt != null) {
                        prSt.close();
                }

                if (conn != null) {
                        conn.close();
                }
        }
}

private static void display(ResultSet myRs) throws SQLException {
        System.out.println("----------------------------");
        System.out.println("login\t password\trole");
        System.out.println("----------------------------");

        while (myRs.next()) {
                String login = myRs.getString("login");
                String password = myRs.getString("password");
                Integer role = myRs.getInt("role");
                System.out.printf("%s \t %s \t\t %d\n", login, password, role);
        }

        System.out.println("----------------------------");
    }
}
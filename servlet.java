import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DatabaseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection dbCxn = DriverManager.getConnection("jdbc:mysql://localhost:33306/UserValues", "root123", "PASSWORD");
            Statement selectThis = dbCxn.createStatement();
            ResultSet rsUsers = selectThis.executeQuery("SELECT * from Users");

            // Process the ResultSet and send response to the frontend
            // convert the ResultSet to JSON or any other format as needed

            dbCxn.close();
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }
    }
}

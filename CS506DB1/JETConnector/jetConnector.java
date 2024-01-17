import java.sql.*;
class MysqlCon {
    public static void main(String args[]) {
        try {
            Connection dbCxn = DriverManager.getConnection(
                "jdbc:mysql://localhost:33306/UserValues", "root", "root123"
            );
            Statement selectThis = dbCxn.createStatement();
            ResultSet rsUsers = selectThis.executeQuery("select * from Users");
            while (rsUsers.next()) {
                System.out.println(rsUsers.getInt(1)
                        + " " + rsUsers.getString(2)
                        + " " + rsUsers.getInt(3)
                        + " " + rsUsers.getInt(4)
                );
            }
            dbCxn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}

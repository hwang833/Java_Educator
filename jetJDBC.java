import java.sql.*;
class MysqlCon{
    public static void main(String args[]){
        try{
            Connection dbCxn=DriverManager.getConnection(
                                "jdbc:mysql://localhost:33306/JET","root","root123"
            );
            Statement selectUsers = dbCxn.createStatement();
            ResultSet rsUsers=selectUsers.executeQuery("select * from Users");
            while (rsUsers.next()){
                System.out.println(rsUsers.getInt(1)
                        + " " + rsUsers.getString(2)
                        + " " + rsUsers.getString(3)
                        + " " + rsUsers.getInt(4)
                        + " " + rsUsers.getInt(5)
                );
            }
            dbCxn.close();
        } catch(Exception e) {System.out.println(e);}
    }
}
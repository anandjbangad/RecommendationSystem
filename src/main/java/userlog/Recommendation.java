package userlog;




import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recommendation
{

    public static void main(String[] args) throws SQLException {

        Connection myConn= null;
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Anand J Bangad\\testDB.db");
            myStmt = myConn.createStatement();
            System.out.println("Opened database successfully");
            myRs = myStmt.executeQuery("select * from users");
//            while (myRs.next()) {
//                System.out.println(myRs.getString("UserID") + ", " + myRs.getString("Keywords"));
//            }
            ArrayList<String> recommenderList = new ArrayList<String>();
            ArrayList<String> userID = new ArrayList<String>();
            ArrayList<List<String>> keywords = new ArrayList<List<String>>();
            int rowLength = 0;
            while (myRs.next()) {
                userID.add(rowLength, myRs.getString("id"));
                String mno = myRs.getString("keywords");
                String pqr[] = mno.split(",");
                List<String> xyz = new ArrayList<String>();

                for (int i = 0; i < pqr.length; i++) {
                    xyz.add(pqr[i]);
                }
                keywords.add(rowLength, xyz);
                rowLength++;
            }
            for (int i = 0; i < rowLength; i++) {
                for (int j = 1; j < rowLength; j++) {
                    int count = 0;
                    for (String item : keywords.get(i)) {
                        if (keywords.get(j).contains(item)) {
                            count++;
                        }
                        if (count >=3){
                            break;
                        }
                    }
                    if (count >= 3) {
                        String m ="";
                        String n="";
                        for (String item : keywords.get(j)) {
                            if ((!keywords.get(i).contains(item))) {
                                recommenderList.add(i, item);
                                System.out.println("Recommended Item for" + " " + userID.get(i) + " " + recommenderList.get(i));
                                if (! (recommenderList.get(i).length()>=3)) {
                                    m = m + recommenderList.get(i) + ",";
                                    PreparedStatement myPstm = myConn.prepareStatement("UPDATE users SET recommendation = ? WHERE id = ?");
                                    myPstm.setString(1, m);
                                    myPstm.setString(2, userID.get(i));
                                    myPstm.executeUpdate();
                                }
//                                  String sql1 = "INSERT INTO Recommendations " + "VALUES ()";
//                                String sql2 = recommenderList.get(i);
//                                myStmt.executeUpdate(sql1 + "VALUES (" + sql2 + ")");
//                                PreparedStatement ps = myConn.prepareStatement("insert into user_log(Recommendations) values(?)");
//                                ps.setString(0,recommenderList.get(i));
                            }

                        }
                        if(recommenderList.size() ==0){
                            recommenderList.add(keywords.get(i).get(i));
                        }

//                            String sql = "INSERT INTO user_log(Recommendations) VALUES(?) WHERE  (UserId =?)";
//                            try (
//
//                                PreparedStatement pstmt = myConn.prepareStatement(sql)) {
//                                pstmt.setString(1, m+recommenderList.get(i));
//                                pstmt.setString(2, userID.get(i));
//                                pstmt.executeUpdate();
//                            }catch (SQLException e) {
//                                    System.out.println(e.getMessage());
//                                }

                        for (String item : keywords.get(i)) {
                            if (!keywords.get(j).contains(item)) {
                                recommenderList.add(j, item);
                                System.out.println("Recommended Item for" +" " + userID.get(j) + " " + recommenderList.get(j));
                                n = n + recommenderList.get(j) + ",";
                                PreparedStatement myPstm = myConn.prepareStatement("UPDATE users SET recommendation = ? WHERE id = ?");
                                myPstm.setString(1,n);
                                myPstm.setString(2,userID.get(j));
                                myPstm.executeUpdate();

//                                String sql1 = "INSERT INTO Recommendations " + "VALUES ()";
//                                String sql2 = recommenderList.get(j);
//                                myStmt.executeUpdate(sql1 + "VALUES (" + sql2 + ")");
                            }

                        }
                    }
//                    if(count>=3){
//                        break;
//                    }
                }
            }
//            for (int i=0; i< recommenderList.size();i++){
//                System.out.println( i + " " + recommenderList.get(i));
//
//
//            }

//            System.out.print(recommenderList.get(1));
            //HashMap<String,List<String>> abc = new HashMap<String, List<String>>();
//            while (myRs.next()){
//                if(!abc.containsKey(myRs.getString("UserID"))){
//                    String mno = myRs.getString("Keywords");
//                    String pqr[] = mno.split(",");
//                    List<String> xyz = null;
//                    for (int i=0; i<pqr.length;i++)
//                        xyz.add(pqr[i]);
//                    abc.put(myRs.getString("'UserID"),xyz);
//                }
//            }
//            //HashMap is Ready



        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
    }
    public String abc(String RequestedId) throws SQLException {
        ResultSet myRs = null;
        Statement myStmt = null;
        Connection myConn = null;
        String response ="";
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Anand J Bangad\\testDB.db");
            myStmt = myConn.createStatement();
            System.out.println("Opened database successfully");
            myRs = myStmt.executeQuery("select * from users");
            String sql = "SELECT recommendation " + "FROM users WHERE id = ?";
            PreparedStatement pstmt  = myConn.prepareStatement(sql);
            pstmt.setString(1,RequestedId);
            ResultSet resultSet = pstmt.executeQuery();
            response = resultSet.getString("recommendation");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
        return response;

    }

}

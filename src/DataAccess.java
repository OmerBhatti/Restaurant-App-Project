
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OMer
 */

public class DataAccess {
    static Connection con;
    static Statement set;
    static ResultSet rs;
    
    static public int getPoint(int cardNo)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","root");
            set=con.createStatement();
            
            rs=set.executeQuery("SELECT points FROM loyalty WHERE card_no = "+Integer.toString(cardNo));
            
            while(rs.next())
            {
                int points = Integer.parseInt(rs.getString(1));
                return points;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    
    static public void setPoints(int points,int cardNo)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","root");
            set=con.createStatement();
            
            set.executeUpdate("UPDATE Loyalty set points = "+points+" WHERE card_no = "+cardNo);
            System.out.println("Updated Points");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();            
        }
    }
    static public int addMember()
    {
        int cardNo=0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","root");
            set=con.createStatement();
            
            //insert new Member
            set.executeUpdate("insert into Loyalty(points) values(50)");
            
            //get latest added member
            rs = set.executeQuery("SELECT * FROM Loyalty ORDER BY card_no DESC");
            
            while(rs.next())
            {
                cardNo = Integer.parseInt(rs.getString(1));
                System.out.println("Member Added with Card no "+cardNo);
                return cardNo;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();            
        }
        return cardNo;
    }
}

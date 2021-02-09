
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OMer
 */
public class Application extends JFrame implements ActionListener{
    JFrame jf;
    JButton addMemberShip;
    JButton exit;
    JButton check;
    Restaurant restaurant;
    JTextField cardno;      
    
    public Application() {
        initGUI();
    }
    
    void initGUI()
    {
        jf = new JFrame("Loyalty Card");
        jf.setLayout(new FlowLayout());
        jf.add(new JLabel("<html><h3 color=red>FREE 50 points on registration.</h3></html>", SwingConstants.CENTER));
        jf.add(new JLabel("Accumulate points and spend them to get a discount.", SwingConstants.CENTER));
        addMemberShip = new JButton("Become Member");
        addMemberShip.addActionListener(this);
        jf.add(addMemberShip);
        exit = new JButton("Order Food");
        exit.addActionListener(this);
        jf.add(exit);
        check = new JButton("Check Points");
        check.addActionListener(this);
        jf.add(check);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setSize(350,180);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void addMember()
    {
        //go to database and add a new member to table
        int cardNo = DataAccess.addMember();
        Application.alert("Your Card # is "+cardNo+"\nYour Points = 50\nwhen you have enough points\nUse this card number for discount\n5000pt = 10% discount");
        jf.dispose();
        
    }
    void checkPointsGUI()
    {
        JFrame jf = new JFrame("Checking");
        jf.setLayout(new FlowLayout());
        JButton check;
        
        cardno = new JTextField(10);
        check=new JButton("check");
        check.addActionListener(this);
        
        jf.add(new JLabel("Enter Card No:"));
        jf.add(cardno);
        jf.add(check);
        
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    void checkPoints()
    {
        //go to data base and get the corresponding points
        int no = Integer.parseInt(cardno.getText());
        int points = DataAccess.getPoint(no);
        Application.alert("Card # "+no+" has "+points+" points");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("Become Member".equals(e.getActionCommand()))
        {
            System.out.println("Make Member");
            addMember();
            restaurant = new Restaurant();
        }
        else if(e.getActionCommand()=="Order Food")
        {
            jf.dispose();
            restaurant = new Restaurant();
        }
        else if(e.getActionCommand()=="Check Points")
        {
            checkPointsGUI();
            jf.dispose();
        }
        else if(e.getActionCommand()=="check")
        {
            checkPoints();
        }
    }
    static void alert(String s){
        JOptionPane.showMessageDialog(null, s);
    }
}

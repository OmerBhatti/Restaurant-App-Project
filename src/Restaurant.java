
import java.awt.*;
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
public class Restaurant {
    //GUI Components
    JFrame jf;
    JPanel[]dishes=null;
    JToggleButton[] added=null;
    JButton confirm;
    //Data
    static String currency = "$";
    int NoOfDishes;
    Menu menu=null;
    EventHandler hnd;
    public Restaurant()
    {
        initializeMenu(11);
        initGUI();
    }

    private void initGUI() {    
        UIManager.put("ToggleButton.select", Color.LIGHT_GRAY);
        jf=new JFrame("Restaurant");
        jf.setLayout(new GridBagLayout());
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.fill=GridBagConstraints.HORIZONTAL;
        constrains.insets = new Insets(5, 5, 5, 5);
        constrains.weighty = 1;
        constrains.weightx = 1;
        
        dishes =new JPanel[NoOfDishes];
        added =new JToggleButton[NoOfDishes];
        
        int xGrid = 0 ,yGrid = 0;
        int columns = 3;
        
        for (int i = 0; i < menu.getSize(); i++)
        {
            yGrid=i/columns;
            if(i%columns==0)
            {
                xGrid=0;
            }
            dishes[i]=new JPanel();
            dishes[i].setLayout(new BorderLayout());
            
            //adding Images
            dishes[i].add(new JLabel(menu.getDishImage(i)),BorderLayout.LINE_START);
            
            //customizing toggle button
            added[i]=new JToggleButton(menu.getDishName(i));
            added[i].setBackground(Color.white);
            dishes[i].add(added[i],BorderLayout.CENTER);
            
            //adding price
            dishes[i].add(new JLabel("Price: "+currency+(menu.getDishPrice(i))),BorderLayout.PAGE_END);
            
            constrains.gridx = xGrid;
            constrains.gridy = yGrid;
            jf.add(dishes[i],constrains);
            xGrid++;
        }
        constrains.gridx = xGrid;
        constrains.gridy = yGrid;
        confirm = new JButton("Place Order");
        hnd=new EventHandler(menu,added,jf);
        confirm.addActionListener(hnd);
        
        jf.add(confirm,constrains);
        jf.setSize(1366, 650);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeMenu(int no) {
        NoOfDishes=no;
        menu=new Menu(NoOfDishes);
        menu.addItem(new Dish("Biryani",150,new ImageIcon("Biryani.jpg")));
        menu.addItem(new Dish("BBQ",200,new ImageIcon("BBQ.jpg")));
        menu.addItem(new Dish("SoftDrink",50,new ImageIcon("Drinks.jpg")));
        menu.addItem(new Dish("Karahi",1000,new ImageIcon("Karahi.jpg")));
        menu.addItem(new Dish("Burger",450,new ImageIcon("Burger.jpg")));
        menu.addItem(new Dish("Pizza",620,new ImageIcon("Pizza.jpg")));
        menu.addItem(new Dish("Naan",10,new ImageIcon("Naan.jpg")));
        menu.addItem(new Dish("IceCream",100,new ImageIcon("icecream.jpg")));
        menu.addItem(new Dish("Tea",30,new ImageIcon("tea.jpg")));
        menu.addItem(new Dish("Coffee",80,new ImageIcon("coffee.jpg")));
        menu.addItem(new Dish("Ramen",150,new ImageIcon("ramen.jpg")));
    }
}

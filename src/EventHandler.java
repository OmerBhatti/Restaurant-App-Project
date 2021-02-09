
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OMer
 */
public class EventHandler implements ActionListener{

    //GUI Components
    JFrame jf;
    JFrame Receiptjf;
    JFrame app;
    JPanel[]dishes=null;
    JButton[] removed=null;
    JButton confirm;
    JSpinner[] quantity;
    JTextField discountGUI;
    int cardNo;
    double discount;
    //data
    boolean hasDiscount=false;
    String preBillText="Bill: ".concat(Restaurant.currency);
    Order order=null;
    Menu menu;
    JToggleButton[] dishAdded=null;
    
    public EventHandler(Menu passedMenu,JToggleButton[] dishes,JFrame application)
    {
        app=application;
        menu=passedMenu;
        dishAdded=dishes;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Place Order"))
        {
            addDishesIntoOrder();
            initConfirmGUI();
            app.setVisible(false);
        }
        else if(e.getActionCommand().equals("Remove"))
        {
            for (int i = 0; i < removed.length; i++) {
                if(removed[i]!=null && e.getSource()==removed[i])
                {
                    //remove order from list
                    if(order.getDish(i)!=null)
                    {
                        order.remove(order.getDishName(i));
                    }                    
                    removed[i]=null;
                    quantity[i].setValue(0);
                    
                    //remove panel from GUI
                    dishes[i].removeAll();
                    dishes[i].repaint();
                    break;
                }
            }
        }
        else if("Confirm Order".equals(e.getActionCommand()))
        {
            String ExtractedText = discountGUI.getText();
            if(ExtractedText.equals("Discount Card No."))
            {
                hasDiscount=false;
                discount=0;
            }
            else
            {
                int cardNo = 0;
                try {
                    cardNo = Integer.parseInt(ExtractedText);
                    int points=DataAccess.getPoint(cardNo);
                    if(points >=5000 )
                    {
                        hasDiscount=true;
                    }
                    else
                    {
                        hasDiscount=false;
                        Application.alert("No Enough Points For Discount \n5000 points required minimum\nYour Points = "+points);
                        discount=0;
                    }
                    if(hasDiscount)
                    {
                        int balancepoints = points-5000;
                        DataAccess.setPoints(balancepoints, cardNo);
                        discount=0.1f;
                    }

                    //500 points on every purchase
                    points=DataAccess.getPoint(cardNo);
                    DataAccess.setPoints(points+500, cardNo);
                } 
                catch (NumberFormatException er)
                {
                    hasDiscount=false;
                    discount=0;
                }               
            }
                    
            initReceiptGUI();
            jf.setVisible(false);
        }
        else if("Save Receipt".equals(e.getActionCommand()))
        {
            String ReceiptNo = SaveReceipt();
            Application.alert("Receipt saved on System.\n"+ReceiptNo);
        }
    }
        
    void initConfirmGUI()
    {
        jf=new JFrame("Confirm Order");
        jf.setLayout(new GridBagLayout());
        quantity=new JSpinner[order.getSize()];
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.insets=new Insets(5, 5, 5, 5);
        constrains.fill=GridBagConstraints.HORIZONTAL;
        constrains.weighty = 0.8;
        constrains.weightx = 0.8;
        
        dishes =new JPanel[order.getSize()];
        removed =new JButton[order.getSize()];
        
        boolean flip=false;
        int xGrid=0,yGrid=0;
        for (int i = 0; i < order.getSize(); i++) 
        {
            dishes[i]=new JPanel();
            dishes[i].setLayout(new GridLayout(4,1));
            dishes[i].add(new JLabel(order.getDishName(i)));
            //adding price
            String str="Price: ".concat(Restaurant.currency);
            dishes[i].add(new JLabel(str.concat(Double.toString(order.getDishPrice(i)))));
            
            //adding quantity selector
            quantity[i]=new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
            dishes[i].add(quantity[i],constrains);
            
            //customizing toggle button
            removed[i]=new JButton("Remove");
            removed[i].setBackground(Color.white);
            removed[i].addActionListener(this);
            dishes[i].add(removed[i],constrains);
            
            constrains.gridx = xGrid;
            constrains.gridy = yGrid;
            jf.add(dishes[i],constrains);
            
            if(flip)
            {
                xGrid=0;
                yGrid++;
            }
            else
            {
                xGrid=1;
            }
            flip=!flip;
        }
        yGrid++;
        constrains.gridx = 0;
        constrains.gridy = yGrid;
        constrains.gridwidth=2;
        
        discountGUI = new JTextField("Discount Card No.");
        jf.add(discountGUI,constrains);
        
        yGrid++;
        constrains.gridx = 0;
        constrains.gridy = yGrid;
        constrains.gridwidth=2;
        
        confirm = new JButton("Confirm Order");
        confirm.addActionListener(this);
        jf.add(confirm,constrains);
        
        
        
        jf.setSize(500, 768);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    void initReceiptGUI()
    {
        JButton save;
        recalculateQuantity();
        Receiptjf=new JFrame("Receipt");
        Receiptjf.setLayout(new BorderLayout());
        JTable table=null;
        String[][] data=new String[order.getSize()+5][4];
        String columnName[]={"Dish Name","Unit Price","Quantity","Grand Price"};
        Receiptjf.setSize(300, 550);
        
        int i,j=0;
        for (i = 0; i < order.getSize(); i++)
        {
            String name=order.getDishName(i);
            double price=order.getDishPrice(i);
            int quant=(int)quantity[i].getValue();
            if(name==null)
            {
                continue;
            }
            if(name!=null && price!=0 && quant!=0)
            {
                data[j][0]=name;
                data[j][1]=Restaurant.currency+Double.toString(price);
                data[j][2]=Integer.toString(quant);
                data[j][3]=Restaurant.currency+Double.toString(price*quant);
                j++;
            }
        }
        
        double TaxAmount=0.05; //5%
        double Tax=order.getTotalPrice()*TaxAmount;
        double discountPrice=order.getTotalPrice()*discount;
        j++;
        data[j][0]="Bill";
        data[j][3]=Restaurant.currency+Double.toString(order.getTotalPrice());
        j++;
        data[j][0]="Tax "+(Double.toString(TaxAmount*100))+"%";
        data[j][3]=Restaurant.currency+Double.toString(Tax);
        j++;
        data[j][0]="Discount "+(Double.toString(Math.round(discount*100)))+"%";
        data[j][3]=Restaurant.currency+Double.toString(Math.round(discountPrice));
        j++;
        data[j][0]="Payable Bill";
        data[j][3]=Restaurant.currency+Double.toString((order.getTotalPrice()+Tax-discountPrice));
        
        table=new JTable(data,columnName);
        JScrollPane tableSP = new JScrollPane(table);
        
        //save receipt button
        save=new JButton("Save Receipt");
        save.addActionListener(this);
        JScrollPane button = new JScrollPane(save);
        
        //adding table and button
        Receiptjf.add(tableSP,BorderLayout.PAGE_START);
        Receiptjf.add(button,BorderLayout.PAGE_END);
        Receiptjf.setVisible(true);
        Receiptjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    String SaveReceipt()
    {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy-hh_mm_ss");  
        String strDate = dateFormat.format(date);  
        String fileName="Reciept#"+strDate+".txt";
        System.out.println(fileName);
        try {
            File file=new File(fileName);
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("DishName\t\tUnit Price\t*\tQuantity\tPrice\n");
            int j=0;
            for (int i = 0; i < order.getSize(); j++,i++)
            {
                int quant=(int)quantity[j].getValue();
                double price=order.getDishPrice(i);
                if(order.getDishName(i)==null)
                {
                    continue;
                }
                if(order.getDishName(i)!=null);
                {
                    pw.print(order.getDishName(i)+"\t\t\t"+price+"\t\t*\t"+Integer.toString(quant)+"\t\t"+Double.toString(price*quant)+"\n");
                }
            }
            double TaxAmount=0.05;
            double Tax=order.getTotalPrice()*TaxAmount;
            double discountPrice=order.getTotalPrice()*discount;
            
            pw.print("\nBill: "+"\t\t\t"+Restaurant.currency+Double.toString(order.getTotalPrice())+"\n");
            pw.print("Tax "+(Double.toString(TaxAmount*100))+"%"+"\t\t"+Restaurant.currency+Double.toString(Tax)+"\n");
            pw.print("Discount "+(Double.toString(Math.round(discount*100)))+"%"+"\t\t"+Restaurant.currency+Double.toString(Math.round(discountPrice))+"\n");
            pw.print("Payable Bill "+"\t\t"+Restaurant.currency+Double.toString(Tax+order.getTotalPrice())+"\n");
            
            pw.close();
            fw.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return fileName;
    }
    
    void addDishesIntoOrder()
    {
        order=new Order(menu.getSize());
        for (int i = 0; i < dishAdded.length; i++) {
            if(dishAdded[i].isSelected())
            {
                order.addItem(new Dish(menu.getDishName(i),menu.getDishPrice(i),menu.getDishImage(i)));
            }
        }
    }
    
    void recalculateQuantity()
    {
        for (int i = 0; i < order.getSize(); i++) {
            order.setQuantity(i, (int)quantity[i].getValue());
        }
    }
}

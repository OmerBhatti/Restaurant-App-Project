/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OMer
 */
public class Order extends Menu{
    
    int[] quantity;
    
    public Order(int size) {
        super(size);
        quantity=new int[size];
    }
    
    void setQuantity(int index,int amount)
    {
        quantity[index]=amount;
    }
    
    void remove(String name)
    {
        for (int i = 0; i < currentSize; i++) {
            if(menuItems[i]!=null &&menuItems[i].getDishName()!=null && menuItems[i].getDishName().equals(name))
            {
                menuItems[i].dishName=null;
                menuItems[i].price=0;
                return;
            }
        }
    }
    
    double getTotalPrice()
    {
        double total=0;
        for (int i = 0; i < currentSize; i++) {
            if(menuItems[i]!=null)
            {
                total+=menuItems[i].getDishPrice()*quantity[i];
            }
        }
        return total;
    }
    
    
}

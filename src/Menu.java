
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OMer
 */
public class Menu {
    Dish[] menuItems  = null;
    int currentSize=0;
    int maxSize;
    
    public Menu(int size)
    {
        maxSize=size;
        if(size<=0)
        {
            maxSize=10;
        }
        menuItems=new Dish[maxSize];
    }
    
    void addItem(Dish dish)
    {
        if(currentSize>=maxSize)
        {
            System.out.println("Limit Reached!");
            return;
        }
        menuItems[currentSize]=new Dish(dish.getDishName(),dish.getDishPrice(),dish.getDishImage());
        currentSize++;
    }
    
    String getDishName(int index)
    {
        if(menuItems[index]!=null)
        {
            return menuItems[index].getDishName();
        }
        return null;
    }
    
    double getDishPrice(int index)
    {
        if(menuItems[index]!=null)
        {
            return menuItems[index].getDishPrice();
        }
        return 0.0;
    }
    ImageIcon getDishImage(int index)
    {
        if(menuItems[index]!=null)
        {
            return menuItems[index].getDishImage();
        }
        return null;
    }
    
    Dish getDish(int index)
    {
        if(menuItems[index]!=null)
        {
            return menuItems[index];
        }
        return null;
    }
    int getSize()
    {
        return currentSize;
    }
    
    void printMenu()
    {
        for (int i = 0; i < currentSize; i++) {
            System.out.println(menuItems[i].getDishName()+" "+menuItems[i].getDishPrice());
        }
    }
}

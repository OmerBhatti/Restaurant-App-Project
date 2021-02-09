
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
public class Dish 
{
    String dishName;
    double price;
    ImageIcon dishImage;
    
    public Dish(String name,double price,ImageIcon image)
    {
        this.dishName=name;
        this.price=price;
        this.dishImage=image;
    }
    
    public String getDishName()
    {
        return dishName;
    }
    
    public double getDishPrice()
    {
        return price;
    }

    public ImageIcon getDishImage() {
        return dishImage;
    }
    
}

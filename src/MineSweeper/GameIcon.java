/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineSweeper;

import javax.swing.ImageIcon;

/**
 *
 * @author mahamudul.hasan
 */
public class GameIcon {
  public static ImageIcon getMineIcon(){
    ImageIcon icon = new ImageIcon("mine.png");
    return icon;
}  
    public static ImageIcon getFlagIcon(){
    ImageIcon icon = new ImageIcon("flag.png");
    return icon;
}
}

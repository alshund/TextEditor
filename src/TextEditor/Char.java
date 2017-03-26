package TextEditor;

import java.awt.*;
import java.awt.event.FocusAdapter;

/**
 * Created by shund on 28.02.2017.
 */
public class Char {
    private Font font;
    private Boolean isSelect;
    private char charElement;
    private int height;
    private int wight;
    private int X;
    private int Y;
    private int numberOfLine;

    public Char(char charElement, Font font){
        isSelect = false;
        this.charElement = charElement;
        this.font = font;
    }

    public void setX(int x){
        this.X = x;
    }
    public void setY(int y){
        this.Y = y;
    }
    public void setHeight (int height){
        this.height = height;
    }
    public void setNumberOfLine(int numberOfLine) {
        this.numberOfLine = numberOfLine;
    }
    public void setWight (int wight){
        this.wight = wight;
    }
    public void setIsSelect (boolean isSelect){
        this.isSelect = isSelect;
    }
    public void setFont(Font font){
        this.font = font;
    }

    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    public int getWight(){
        return wight;
    }
    public  int getHeight(){
        return height;
    }
    public int getNumberOfLine(){
        return numberOfLine;
    }
    public char getCharElement(){
        return charElement;
    }
    public String getStringElement (){
        return Character.toString(charElement);
    }
    public boolean isSelect (){
        return isSelect;
    }


    public void setFontType(String type){
        font = new Font(type, getFontStyle(), getFontSize());
    }
    public void setFontSize(int size){
        font = new Font(getFontType(), getFontStyle(), size);
    }
    public void setFontStyle(int style){
        font = font.deriveFont(style);
    }
    public String getFontType(){
        return font.getFontName();
    }
    public int getFontSize(){
        return font.getSize();
    }
    public int getFontStyle(){
        return  font.getStyle();
    }

}

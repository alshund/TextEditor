package TextEditor;

import java.awt.*;

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
    private int maxHeight;

    public Char(char charElement){
        isSelect = false;
        this.charElement = charElement;
        font = new Font(Font.MONOSPACED, 0, 16);
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
    public void setMaxHeight(int maxHeight) {
     this.maxHeight = maxHeight;
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
    public char getCharElement(){
        return charElement;
    }
    public String getStringElement (){
        return Character.toString(charElement);
    }
    public boolean isSelect (){
        return isSelect;
    }

    public boolean isElementHere (Point click){
        return (click.getX() >= X &&
                click.getY() <= Y &&
                click.getX() <= X + wight &&
                click.getY() >= Y - height);
    }
    public boolean isElementHere (Point firstPoint, Point secondPoint){
        Point upPoint = firstPoint.getY() < secondPoint.getY() ? firstPoint : secondPoint;
        Point downPoint = firstPoint.getY() < secondPoint.getY() ? secondPoint : firstPoint;
        if (Y < downPoint.getY() || Y - maxHeight > upPoint.getY()){
            return ((X >= upPoint.getX() && Y - maxHeight < upPoint.getY() && Y >= upPoint.getY()) ||
                    (X <= downPoint.getX() && Y - maxHeight < downPoint.getY() && Y >= downPoint.getY()) ||
                    (Y < downPoint.getY() && Y - maxHeight >= upPoint.getY()));
        } else{
            Point leftPoint = firstPoint.getX() < secondPoint.getX() ? firstPoint : secondPoint;
            Point rightPoint = firstPoint.getX() < secondPoint.getX() ? secondPoint : firstPoint;
            return (X >= leftPoint.getX() && X <= rightPoint.getX() &&
                    Y - maxHeight < leftPoint.getY() && Y > leftPoint.getY() &&
                    Y - maxHeight < rightPoint.getY() && Y > rightPoint.getY());
        }
    }
    public void setFontType(String type){
        font = new Font(type, 0, getFontSize());
    }
    public void setFontSize(int size){
        font = new Font(getFontType(), 0, size);
    }
    public String getFontType(){
        return font.getFontName();
    }
    public int getFontSize(){
        return font.getSize();
    }

}

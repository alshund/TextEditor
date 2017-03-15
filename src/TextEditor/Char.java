package TextEditor;

import java.awt.*;

/**
 * Created by shund on 28.02.2017.
 */
public class Char {
    private FrameWindow frameWindow;
    private TextPanel textPanel;
    private Font font;
    private Boolean isSelect;
    private char charElement;
    private int height;
    private int wight;
    private int X;
    private int Y;
    private int numberOfLine;

    public Char(char charElement, FrameWindow frameWindow){
        this.textPanel = frameWindow.getTextPanel();
        this.charElement = charElement;
        isSelect = false;
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
    public void setWight (int wight){
        this.wight = wight;
    }
    public void setIsSelect (boolean isSelect){
        this.isSelect = isSelect;
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
        int height = textPanel.getText().get(numberOfLine).getMaxHigh();
        Point upPoint = firstPoint.getY() < secondPoint.getY() ? firstPoint : secondPoint;
        Point downPoint = firstPoint.getY() < secondPoint.getY() ? secondPoint : firstPoint;
        if (Y < downPoint.getY() || Y - height > upPoint.getY()){
            return ((X >= upPoint.getX() && Y - height < upPoint.getY() && Y >= upPoint.getY()) ||
                    (X <= downPoint.getX() && Y - height < downPoint.getY() && Y >= downPoint.getY()) ||
                    (Y < downPoint.getY() && Y - height >= upPoint.getY()));
        } else{
            Point leftPoint = firstPoint.getX() < secondPoint.getX() ? firstPoint : secondPoint;
            Point rightPoint = firstPoint.getX() < secondPoint.getX() ? secondPoint : firstPoint;
            return (X >= leftPoint.getX() && X <= rightPoint.getX()/* &&
                    Y - height < leftPoint.getY() && Y > leftPoint.getY() &&
                    Y - height < rightPoint.getY() && Y > rightPoint.getY()*/);
        }
    }

}

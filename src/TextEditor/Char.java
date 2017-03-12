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
    private int coordinateX;
    private int coordinateY;
    private int numberOfLine;

    public Char(char charElement, FrameWindow frameWindow){
        this.textPanel = frameWindow.getTextPanel();
        this.charElement = charElement;
        isSelect = false;
    }

    public void setCoordinateX(int coordinateX){
        this.coordinateX = coordinateX;
    }
    public void setCoordinateY(int coordinateY){
        this.coordinateY = coordinateY;
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

    public int getCoordinateX(){
        return coordinateX;
    }
    public int getCoordinateY(){
        return coordinateY;
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
        return (click.getX() >= coordinateX &&
                click.getY() <= coordinateY &&
                click.getX() <= coordinateX + wight &&
                click.getY() >= coordinateY - height);
    }
    public boolean isElementHere (Point firstClick, Point secondClick){
        int height = textPanel.getText().get(numberOfLine).getMaxHigh();
        Point leftClick = (firstClick.getX() < secondClick.getX() ? firstClick : secondClick);
        Point upClick = (firstClick.getY() < secondClick.getY() ? secondClick : firstClick);
        Point rightClick = (firstClick.getX() < secondClick.getX() ? secondClick : firstClick);
        Point downClick = (firstClick.getY() < secondClick.getY() ? firstClick : secondClick);
        if (coordinateY <= downClick.getY() && coordinateY - height >= upClick.getY()){
            return ((coordinateY >= upClick.getX()) && coordinateY - height < upClick.getY() && coordinateY >= upClick.getY() ||
                    (coordinateY <= downClick.getX()) && coordinateY - height < downClick.getY() && coordinateY >= downClick.getY() ||
                    (coordinateY - height >= upClick.getY() && coordinateY < downClick.getY()));

        } else {
            return ((coordinateY - height <= leftClick.getY() && coordinateY >= leftClick.getY()) &&
                    (coordinateX >= leftClick.getY() && coordinateX <= rightClick.getX()) &&
                    (coordinateY - height <= rightClick.getY() && coordinateY >= rightClick.getY()));

        }
    }

}

package TextEditor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shund on 28.02.2017.
 */
public class Line {
    private FrameWindow frameWindow;
    private TextPanel textPanel;
    private Caret caret;
    private List<Char> line = new LinkedList<Char>();
    private int maxHigh;
    private int maxLength;
    private int numberOfLine;
    private int coordinateY;


    public Line(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        textPanel = frameWindow.getTextPanel();
        caret = textPanel.getCaret();
        maxHigh = 15;
        maxLength = 0;

    }

    public List<Char> getLine(){
        return line;
    }
    public int getMaxHigh(){
        return maxHigh;
    }
    public int getMaxLength(){
        return maxLength;
    }
    public int getNumberOfLine(){
        return numberOfLine;
    }
    public int getCoordinateY(){
        return coordinateY;
    }
    public int size(){
        return line.size();
    }
    public int indexOf(Char charElement){
        return line.indexOf(charElement);
    }

    public void setMaxHigh(int hight){
        maxHigh = hight;
    }
    public void setMaxLength(int length){
        maxLength = length;
    }
    public void setNumberOfLine(int numberOfLine){
        this.numberOfLine = numberOfLine;
    }
    public void setCoordinateY (int coordinateY){
        this.coordinateY = coordinateY;
    }

    public void addChar(int coordinateX, char charElement){
        line.add(coordinateX, new Char(charElement, frameWindow));
        System.out.println(coordinateX + "-" + charElement);
    }
    public void add(Char charElement){
        line.add(charElement);
    }
    public void borderOfLine(Point click){
        if (coordinateY - maxHigh <= click.getY() && coordinateY >= click.getY() && maxLength <= click.getX()){
            caret.setCaretListX(line.size());
            caret.setCaretListY(numberOfLine);
        } else if(click.getX() <= 10){
            caret.setCaretListX(0);
        }
    }
    public void remove(int x, int y){
        int index = x;
        while(index < y){
            this.line.remove(x);
            index++;
        }
    }
    public Line copyFromX1toX2(int x1, int x2){
        Line newLine = new Line(frameWindow);
        for (int index = x1; index < x2; index++){
            newLine.add(this.line.get(index));
        }
        return newLine;
    }

}
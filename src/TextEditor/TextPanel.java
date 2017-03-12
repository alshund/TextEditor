package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shund on 28.02.2017.
 */
public class TextPanel extends JComponent {
    private FrameWindow frameWindow;
    private Caret caret;
    private List<Line> text = new ArrayList<Line>();
    private int index;

    public TextPanel (FrameWindow frameWindow){
        this.frameWindow = frameWindow;
    }

    public Caret getCaret(){
        return caret;
    }
    public List<Line> getText(){
        return text;
    }

    public void createInput(){
        caret = new Caret(frameWindow);
        CaretTimer caretTimer = new CaretTimer(this);
        Line newLine = new Line(frameWindow);
        text.add(newLine);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D= (Graphics2D) g;
        int coordinateY = 0, numberOfLine = 0, xMax = 0;
        for (Line line:text){
            coordinateY += line.getMaxHigh();
            int coordinateX = 10, numberOfChar = 0;
            for(Char charElement:line.getLine()){
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(charElement.getStringElement(), coordinateX, coordinateY);
                FontMetrics fontMetrics = graphics2D.getFontMetrics();


                if (charElement.isSelect()) {
                    graphics2D.setColor(Color.gray);
                    Rectangle2D rect = new Rectangle
                            (coordinateX, coordinateY-line.getMaxHigh()+3, fontMetrics.stringWidth(charElement.getStringElement()), line.getMaxHigh()-1);
                    graphics2D.fill(rect);
                    graphics2D.setColor(Color.blue);
                }


                charElement.setCoordinateX(coordinateX);
                charElement.setCoordinateY(coordinateY);
                charElement.setHeight(fontMetrics.getHeight());
                charElement.setWight(fontMetrics.stringWidth(charElement.getStringElement()));
                coordinateX += fontMetrics.stringWidth(charElement.getStringElement()) + 1;
                numberOfChar++;
                if(caret.getCaretListX() == numberOfChar && caret.getCaretListY() == numberOfLine){
                    caret.setCaretCoordinateX(coordinateX);
                    caret.setCaretCoordinateY(coordinateY);
                }
            }
            line.setMaxLength(coordinateX);
            line.setNumberOfLine(numberOfLine);
            line.setCoordinateY(coordinateY);
            xMax = xMax < coordinateX ? coordinateX : xMax;
            if(caret.getCaretListX() == 0 && caret.getCaretListY() == numberOfLine){
                caret.setCaretCoordinateX(10);
                caret.setCaretCoordinateY(coordinateY);
            }
            numberOfLine++;
        }
        setPreferredSize(new Dimension(xMax + 50, coordinateY + 50));
    }
    public void mouseClick(Point point){
        for (Line line: text){
            line.borderOfLine(point);
            for(Char charElement: line.getLine()){
                if(charElement.isElementHere(point)){
                    caret.setCaretListY(text.indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
        frameWindow.unloadFrameWindow();
    }
    public void mouseClick (Point firstClick, Point secondClick){
        for (Line line: text){
            line.borderOfLine(secondClick);
            for (Char charElement: line.getLine()){
                charElement.setIsSelect(charElement.isElementHere(firstClick, secondClick));
                if (charElement.isElementHere(secondClick)){
                    caret.setCaretListY(text.indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
        frameWindow.unloadFrameWindow();
    }
    public void addNewLine(){
        int Y = caret.getCaretListY();
        int X = caret.getCaretListX();
        Line newLine = text.get(Y).copyFromX1toX2(X, text.get(Y).size());
        text.get(Y).remove(X, text.get(Y).size());
        text.add(Y + 1, newLine);
        caret.setCaretListX(0);
        caret.incrementY();
    }

    public void enterKey(){
        addNewLine();
    }
    public void deleteKey(){
        if (caret.getCaretListX() == text.get(caret.getCaretListY()).size() && caret.getCaretListY() == text.size() - 1){
        } else if (caret.getCaretListX() == text.get(caret.getCaretListY()).size()){
            if (text.get(caret.getCaretListY() + 1).size() != 0){
                for (Char charElement:text.get(caret.getCaretListY() + 1).getLine()){
                    text.get(caret.getCaretListY()).add(charElement);
                }
            }
            text.remove(caret.getCaretListY() + 1);
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX(), caret.getCaretListX() + 1);
        }
    }
    public void backSpaceKey(){
        if (caret.getCaretListX() == 0 && caret.getCaretListY() == 0){
        } else if (caret.getCaretListX() == 0){
            if (text.get(caret.getCaretListY()).size() != 0){
                for (Char charElement: text.get(caret.getCaretListY()).getLine()){
                    text.get(caret.getCaretListY() - 1).getLine().add(charElement);
                }
            }
            text.remove(caret.getCaretListY());
            caret.decrementX();
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX() - 1, caret.getCaretListX());
            caret.decrementX();
        }
    }

    public void insertKeyChar(char charKey){
        text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), charKey);
        caret.incrementX();
    }
}

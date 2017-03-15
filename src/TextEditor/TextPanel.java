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

    private void setFont(Graphics2D graphics2D){
        for (Line line : text){
            for (Char charElement : line.getLine()){
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                line.setMaxHigh(fontMetrics.getHeight());
            }
            if (line.getMaxHigh() == 0){
                line.setMaxHigh(10);
            }
        }
    }
    private int setChar(Char charElement, int X, int Y, Graphics2D graphics2D){
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        charElement.setX(X);
        charElement.setY(Y);
        charElement.setHeight(fontMetrics.getHeight());
        charElement.setWight(fontMetrics.stringWidth(charElement.getStringElement()));
        return (fontMetrics.stringWidth(charElement.getStringElement()) + 1);
    }
    private void setCaret(int X, int Y, int numberOfLine, int numberOfChar){
        if (caret.getCaretListX() == numberOfChar && caret.getCaretListY() == numberOfLine){
            caret.setCaretCoordinateX(X);
            caret.setCaretCoordinateY(Y);
        } else if (caret.getCaretListX() == 0 && caret.getCaretListY() == numberOfLine){
            caret.setCaretCoordinateX(10);
            caret.setCaretCoordinateY(Y);
        }
    }
    private void setLine(Line line,  int maxLength, int numberOfLine, int Y){
        line.setMaxLength(maxLength);
        line.setNumberOfLine(numberOfLine);
        line.setCoordinateY(Y);
    }
    private void createSelectionArea(Line line, Char charElement, Graphics2D graphics2D, int X, int Y){
        if (charElement.isSelect()){
            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            graphics2D.setColor(new Color(200, 200, 200, 120));
            Rectangle2D rectangle2D = new Rectangle(X, Y - line.getMaxHigh(), fontMetrics.stringWidth(charElement.getStringElement()), line.getMaxHigh());
            graphics2D.draw(rectangle2D);
            graphics2D.fill(rectangle2D);
        }
    }
    private void paintChar(Graphics2D graphics2D){
        int Y = 10, numberOfLine = 0;
        for (Line line : text){
            Y += line.getMaxHigh();
            int X = 10, numberOfChar = 0;
            for (Char charElement : line.getLine()){
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(charElement.getStringElement(), X, Y);
                createSelectionArea(line, charElement, graphics2D, X, Y);
                numberOfChar++;
                X += setChar(charElement, X, Y, graphics2D);
                setCaret(X, Y, numberOfLine, numberOfChar);
            }
            setLine(line, X, numberOfLine, Y);
            setCaret(X, Y, numberOfLine, numberOfChar);
            numberOfLine++;
        }
    }
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D= (Graphics2D) graphics;
        setFont(graphics2D);
        paintChar(graphics2D);
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




    public void mouseClick(Point point){
        for (Line line: text){
            line.borderOfLine(point);
            for (Char charElement : line.getLine()){
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
            caret.setCaretListX(getText().get(caret.getCaretListY() - 1).size());
            if (text.get(caret.getCaretListY()).size() != 0){
                for (Char charElement: text.get(caret.getCaretListY()).getLine()){
                    text.get(caret.getCaretListY() - 1).getLine().add(charElement);
                }
            }
            text.remove(caret.getCaretListY());
            caret.decrementY();
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX() - 1, caret.getCaretListX());
            caret.decrementX();
        }
    }

    public void rightSelection(){
        caret.incrementX();
        if (caret.getCaretListX() != 0){
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            for (Line line : text){
                for (Char charElement : line.getLine()){
                    if (!charElement.isSelect()){
                        charElement.setIsSelect(charElement.isElementHere(new Point (X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))){
                        if (text.indexOf(line) == text.size() - 1 && line.getLine().indexOf(charElement) == line.size() - 1)
                        {
                            charElement.setIsSelect(true);
                        } else {
                            charElement.setIsSelect(false);
                        }
                    }
                }
            }
        }

    }
    public void leftSelection(){
        caret.decrementX();
        if (caret.getCaretListX() != text.get(caret.getCaretListY()).getLine().size()){
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getY() - 1;
            for (Line line : text){
                for (Char charElement : line.getLine()){
                    if (!charElement.isSelect()){
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))){
                        if (text.indexOf(line) == 0 && line.indexOf(charElement) == 0){
                            charElement.setIsSelect(true);
                        } else{
                            charElement.setIsSelect(false);
                        }
                    }
                }
            }
        }

    }
    public void upSelection(){
        int firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        caret.decrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (!charElement.isSelect()){
                    charElement.setIsSelect((charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))));
                }else if (charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))){
                    charElement.setIsSelect(false);
                }
            }
        }
    }
    public void downSelection(){
        int firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        caret.incrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (!charElement.isSelect()){
                    charElement.setIsSelect(charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY)));
                } else if (charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))){
                    charElement.setIsSelect(false);
                }
            }
        }
    }

    public void insertKeyChar(char charKey){
        text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), charKey);
        caret.incrementX();
    }
}

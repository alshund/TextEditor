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
        }
        if (caret.getCaretListX() == 0 && caret.getCaretListY() == numberOfLine){
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

    public void deletePreviousChar(){
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
    public void deleteNextChar(){
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

    public boolean deleteSelectedText(){
        boolean newLine = false;
        for (int indexY = 0; indexY < text.size(); indexY++){
            newLine = true;
            int indexX = 0;
            while (indexX != text.get(indexY).getLine().size()){
                if (text.get(indexY).getLine().get(indexX).isSelect()){
                    if (newLine){
                        caret.setCaretListX(indexX);
                        caret.setCaretListY(indexY);
                        newLine = false;
                    }
                    deleteNextChar();
                    indexX--;
                }
                indexX++;
                if (!newLine && caret.getCaretListX() == text.get(caret.getCaretListY()).getLine().size()){
                    deleteNextChar();
                }
            }
        }
        return !newLine;
    }
    public void enterKey(){
        deleteSelectedText();
        addNewLine();
    }
    public void deleteKey(){
        if(!deleteSelectedText()){
            deleteNextChar();
        }
    }
    public void backSpaceKey(){
       if(!deleteSelectedText()){
           deletePreviousChar();
       }
    }

    public void rightSelection(){
        int beforeIncrement = caret.getCaretListX();
        caret.incrementX();
        int afterIncrement = caret.getCaretListX();
        if (caret.getCaretListX() != 0 && beforeIncrement != afterIncrement){
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
        int beforeDecrement = caret.getCaretListX();
        caret.decrementX();
        int afterDecrement = caret.getCaretListX();
        if (caret.getCaretListX() != text.get(caret.getCaretListY()).getLine().size() && beforeDecrement != afterDecrement){
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
    public void falseAlSelection(){
        for (Line line : text){
           for (Char charElement : line.getLine()){
               charElement.setIsSelect(false);
           }
        }
    }



    public void insertKeyChar(char charKey){
        deleteSelectedText();
        text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), charKey);
        caret.incrementX();
    }
}

package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by shund on 28.02.2017.
 */
public class TextPanel extends JComponent {
    private FrameWindow frameWindow;
    private Caret caret;
    private Text text;

    public TextPanel (FrameWindow frameWindow){
        this.frameWindow = frameWindow;
    }

    public Text getText(){
        return text;
    }


    public void drawCaret(){
        Graphics2D graphics2D = (Graphics2D) this.getGraphics();
        graphics2D.drawString("|", caret.getCaretCoordinateX(), caret.getCaretCoordinateY());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2D.setColor(this.getBackground());
        graphics2D.drawString("|", caret.getCaretCoordinateX(), caret.getCaretCoordinateY());
//        textPanel.repaint();
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
        for (Line line : text.getText()){
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

    public void createInput(){
        text = new Text(getCaret());
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

    public void unicodeKey(char unicodeElement){
        text.insertKeyChar(unicodeElement);
    }
    public void enterKey(){
        text.deleteSelectedText();
        text.enterLine();
    }
    public void deleteKey(){
        if(!text.deleteSelectedText()){
            text.deleteNextChar();
        }
    }
    public void backSpaceKey(){
       if(!text.deleteSelectedText()){
           text.deletePreviousChar();
       }
    }











}

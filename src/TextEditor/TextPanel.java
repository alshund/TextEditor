package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * Created by shund on 28.02.2017.
 */
public class TextPanel extends JComponent {
    private FrameWindow frameWindow;
    private Text text;
    private Caret caret;

    public TextPanel (FrameWindow frameWindow){
        this.frameWindow = frameWindow;
    }


    private void setFont(Graphics2D graphics2D){
        for (Line line : text.getText()){
            for (Char charElement : line.getLine()){
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                line.setMaxHigh(fontMetrics.getHeight());
                charElement.setMaxHeight(line.getMaxHigh());
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


    public void createInput(){
        text = new Text();
        caret = new Caret(getText());
        CaretTimer caretTimer = new CaretTimer(this);
        Line newLine = new Line(    );
        text.add(newLine);
    }

    public void deletePreviousChar() {
        if (caret.isCaretInTheBeginOfText()) {
        } else if (caret.isCaretInTheBeginOfTheLine()) {
            caret.setCaretListX(text.get(caret.getCaretListY() - 1).size());
            if (text.get(caret.getCaretListY()).size() != 0) {
                for (Char charElement : text.getText().get(caret.getCaretListY()).getLine()) {
                    text.get(caret.getCaretListY() - 1).getLine().add(charElement);
                }
            }
            text.removeLine(caret.getCaretListY());
            caret.decrementY();
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX() - 1, caret.getCaretListY());
            caret.decrementX();
        }
    }
    public void deleteNextChar() {
        if (caret.isCaretInTheEndOfText()) {
        } else if (caret.isCaretInTheEndOfLine()) {
            if (text.get(caret.getCaretListY() + 1).size() != 0) {
                for (Char charElement : text.get(caret.getCaretListY() + 1).getLine()) {
                    text.get(caret.getCaretListY()).add(charElement);
                }
            }
            text.removeLine(caret.getCaretListY() + 1);
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX(), caret.getCaretListX() + 1);
        }
    }
    public boolean deleteSelectedText() {
        boolean newLine = false;
        for (int indexY = 0; indexY < text.getText().size(); indexY++) {
            newLine = true;
            int indexX = 0;
            while (indexX != text.get(indexY).getLine().size()) {
                if (text.get(indexY).getLine().get(indexX).isSelect()) {
                    if (newLine) {
                        caret.setCaretListX(indexX);
                        caret.setCaretListY(indexY);
                        newLine = false;
                    }
                    deleteNextChar();
                    indexX--;
                }
                indexX++;
                if (!newLine && caret.isCaretInTheEndOfLine()) {
                    deleteNextChar();
                }
            }
        }
        return !newLine;
    }

    public void leftSelection() {
        int beforeDecrement = caret.getCaretListX();
        caret.decrementX();
        int afterDecrement = caret.getCaretListX();
        java.util.List<Char> line1 = text.get(caret.getCaretListY()).getLine();
        if (!caret.isCaretInTheEndOfLine() && beforeDecrement != afterDecrement) {
            int X = line1.get(caret.getCaretListX()).getX() + 1;
            int Y = line1.get(caret.getCaretListX()).getY() - 1;
            for (Line line : text.getText()) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        if (text.getText().indexOf(line) == 0 && line.indexOf(charElement) == 0) {
                            charElement.setIsSelect(true);
                        } else {
                            charElement.setIsSelect(false);
                        }
                    }
                }
            }
        }
    }
    public void rightSelection() {
        int beforeIncrement = caret.getCaretListX();
        caret.incrementX();
        int afterIncrement = caret.getCaretListX();
        if (!caret.isCaretInTheBeginOfTheLine() && beforeIncrement != afterIncrement) {
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            for (Line line : text.getText()) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        if (text.getText().indexOf(line) == text.getText().size() - 1 && line.getLine().indexOf(charElement) == line.size() - 1) {
                            charElement.setIsSelect(true);
                        } else {
                            charElement.setIsSelect(false);
                        }
                    }
                }
            }
        }

    }
    public void upSelection() {
        int firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        caret.decrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : text.getText()) {
            for (Char charElement : line.getLine()) {
                if (!charElement.isSelect()) {
                    charElement.setIsSelect((charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))));
                } else if (charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))) {
                    charElement.setIsSelect(false);
                }
            }
        }
    }
    public void downSelection() {
        int firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        caret.incrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : text.getText()) {
            for (Char charElement : line.getLine()) {
                if (!charElement.isSelect()) {
                    charElement.setIsSelect(charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY)));
                } else if (charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))) {
                    charElement.setIsSelect(false);
                }
            }
        }
    }

    public void newLine() {
        Line newLine = text.get(caret.getCaretListY()).copyFromX1toX2(caret.getCaretListX(), text.get(caret.getCaretListY()).size());
        text.getText().get(caret.getCaretListY()).remove(caret.getCaretListX(), text.get(caret.getCaretListY()).size());
        text.getText().add(caret.getCaretListY() + 1, newLine);
        caret.setCaretListX(0);
        caret.incrementY();
    }


    public void copy() {
        String string = "";
        for (Line line : text.getText()){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    string += charElement.getStringElement();
                }
            }
            if (line.getLine().size() != 0 && line.getLine().get(line.getLine().size()-1).isSelect()){
                string += "\n";
            }
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, null);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Can't copy text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }
    public void paste() {
        try{
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String string = (String) clipboard.getData(DataFlavor.stringFlavor);
            deleteSelectedText();
            for (int index = 0; index < string.length(); index++){
                if (string.charAt(index) == '\n'){
                    newLine();
                } else{
                    text.getText().get(caret.getCaretListY()).addChar(caret.getCaretListX(), string.charAt(index));
                    caret.incrementX();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't past text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void cut(){
        String string = "";
        for (Line line : text.getText()){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    string += charElement.getStringElement();
                }
            }
            if (line.getLine().size() != 0 && line.getLine().get(line.getLine().size()-1).isSelect()){
                string += "\n";
            }
        }
        StringSelection data = new StringSelection(string);
        try {
            deleteSelectedText();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, null);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Can't copy text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }








    public void mouseClick(Point point){
        for (Line line: text.getText()){
            caret.borderOfLine(point, line);
            for (Char charElement : line.getLine()){
                if(charElement.isElementHere(point)){
                    caret.setCaretListY(text.getText().indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
        frameWindow.unloadFrameWindow();
    }
    public void mouseClick (Point firstClick, Point secondClick){
        for (Line line: text.getText()){
            caret.borderOfLine(secondClick, line);
            for (Char charElement: line.getLine()){
                charElement.setIsSelect(charElement.isElementHere(firstClick, secondClick));
                if (charElement.isElementHere(secondClick)){
                    caret.setCaretListY(text.getText().indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
        frameWindow.unloadFrameWindow();
    }

    public Text getText(){
        return text;
    }
    public Caret getCaret(){
        return caret;
    }

}

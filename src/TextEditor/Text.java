package TextEditor;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Created by shund on 16.03.2017.
 */
public class Text {
    private TextPanel textPanel;
    private Caret caret;
    private Font font;
    private int fontStyle;
    private List<Line> text = new ArrayList<Line>();

    public Text(TextPanel textPanel){
        this.textPanel = textPanel;
        fontStyle = Font.PLAIN;
        font = new Font(Font.MONOSPACED, fontStyle, 12);
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void createInput(){
        caret = new Caret();
        caretTimer();
        text.add(new Line());
    }
    public void caretTimer(){
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                textPanel.drawCaret();
            }
        }, 500, 1000);
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void incrementX(){
        if (isCaretInTheEndOfText()){
        } else if (!isCaretInTheEndOfLine()){
            caret.setCaretListX(caret.getCaretListX() + 1);
        } else if(!isCaretInTheLastLine()){
            caret.setCaretListY(caret.getCaretListY() + 1);
            caret.setCaretListX(0);
        }
    }
    public void incrementY(){
        if(!isCaretInTheLastLine()){
            caret.setCaretListY(caret.getCaretListY() + 1);
            if (isCaretAfterTheLineEnd()){
                caret.setCaretListX(text.get(caret.getCaretListY()).size());
            }
        } else {
            caret.setCaretListX(text.get(caret.getCaretListY()).getLine().size());
        }
    }
    public void decrementX(){
        if (isCaretInTheBeginOfText()){
        }else if(!isCaretInTheBeginOfTheLine()){
            caret.setCaretListX(caret.getCaretListX() - 1);
        }else if (!isCaretInTheFirstLine()){
            caret.setCaretListY(caret.getCaretListY() - 1);
            caret.setCaretListX(text.get(caret.getCaretListY()).size());
        }

    }
    public void decrementY(){
        if(!isCaretInTheFirstLine()){
            caret.setCaretListY(caret.getCaretListY() - 1);
            if (isCaretAfterTheLineEnd()){
                caret.setCaretListX(text.get(caret.getCaretListY()).size());
            }
        } else{
            caret.setCaretListX(0);
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void leftSelection() {
        int beforeDecrement = caret.getCaretListX();
        decrementX();
        int afterDecrement = caret.getCaretListX();
        java.util.List<Char> line1 = text.get(caret.getCaretListY()).getLine();
        if (!isCaretInTheEndOfLine() && beforeDecrement != afterDecrement) {
            int X = line1.get(caret.getCaretListX()).getX() + 1;
            int Y = line1.get(caret.getCaretListX()).getY() - 1;
            for (Line line : text) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        charElement.setIsSelect(false);
                    }
                }
            }
        }
    }
    public void rightSelection() {
        int beforeIncrement = caret.getCaretListX();
        incrementX();
        int afterIncrement = caret.getCaretListX();
        if (!isCaretInTheBeginOfTheLine() && beforeIncrement != afterIncrement) {
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            for (Line line : text) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        charElement.setIsSelect(false);
                    }
                }
            }
        }

    }
    public void upSelection() {
        int firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        decrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : getText()) {
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
        incrementY();
        int secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
        int secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
        for (Line line : text) {
            for (Char charElement : line.getLine()) {
                if (!charElement.isSelect()) {
                    charElement.setIsSelect(charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY)));
                } else if (charElement.isElementHere(new Point(firstX, firstY), new Point(secondX, secondY))) {
                    charElement.setIsSelect(false);
                }
            }
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void deletePreviousChar() {
        if (isCaretInTheBeginOfText()) {
        } else if (isCaretInTheBeginOfTheLine()) {
            boolean isLineIsEmpty = text.get(caret.getCaretListY()).size() == 0;
            int endOfPreviousLine = text.get(caret.getCaretListY() - 1).size();
            caret.setCaretListX(endOfPreviousLine);
            if (!isLineIsEmpty) {
                for (Char charElement : text.get(caret.getCaretListY()).getLine()) {
                    text.get(caret.getCaretListY() - 1).getLine().add(charElement);
                }
            }
            removeLine(caret.getCaretListY());
            decrementY();
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX() - 1, caret.getCaretListX());
            decrementX();
        }
    }
    public void deleteNextChar() {
        if (isCaretInTheEndOfText()) {
        } else if (isCaretInTheEndOfLine()) {
            boolean isNextLineIsEmpty = text.get(caret.getCaretListY() + 1).size() == 0;
            if (!isNextLineIsEmpty) {
                for (Char charElement : text.get(caret.getCaretListY() + 1).getLine()) {
                    text.get(caret.getCaretListY()).add(charElement);
                }
            }
            removeLine(caret.getCaretListY() + 1);
        } else {
            text.get(caret.getCaretListY()).remove(caret.getCaretListX(), caret.getCaretListX() + 1);
        }
    }
    public boolean deleteSelectedText() {
        boolean newLine = false;
        for (int indexY = 0; indexY < text.size(); indexY++) {
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
                if (!newLine && isCaretInTheEndOfLine()) {
                    deleteNextChar();
                }
            }
        }
        return !newLine;
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void borderOfLine(Point point, Line line){
        boolean isPointYMoreThenLineUpCoordinateY = line.getCoordinateY() - line.getMaxHigh() <= point.getY();
        if (isPointYMoreThenLineUpCoordinateY){
            caret.setCaretListY(line.getNumberOfLine());
            caret.setCaretListX(point.getX() <= 10 && line.getCoordinateY() >= point.getY() ? 0 : line.size());
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void newLine() {
        int Y = caret.getCaretListY();
        int X = caret.getCaretListX();
        Line newLine = text.get(Y).copyFromX1toX2(X, text.get(Y).size());
        text.get(Y).remove(X, text.get(Y).size());
        text.add(Y + 1, newLine);
        caret.setCaretListX(0);
        incrementY();
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void copy() {
        String element = "";
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    element += charElement.getStringElement();
                }
            }
            if (line.getLine().size() != 0 && line.getLine().get(line.getLine().size()-1).isSelect()){
                element += "\n";
            }
        }
        StringSelection data = new StringSelection(element);
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
                    text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), string.charAt(index));
                    incrementX();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't past text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }
    public void cut(){
        String string = "";
        for (Line line : text){
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
    /*----------------------------------------------------------------------------------------------------------------*/
    public void mouseClick(Point point){
        falseAlSelection();
        for (Line line: text){
            borderOfLine(point, line);
            for (Char charElement : line.getLine()){
                if(charElement.isElementHere(point)){
                    caret.setCaretListY(text.indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
    }
    public void mouseClick (Point firstPoint, Point secondPoint){
        falseAlSelection();
        for (Line line: text){
            borderOfLine(secondPoint, line);
            for (Char charElement: line.getLine()){
                charElement.setIsSelect(charElement.isElementHere(firstPoint, secondPoint));
                if (charElement.isElementHere(secondPoint)){
                    caret.setCaretListY(text.indexOf(line));
                    caret.setCaretListX(line.indexOf(charElement) + 1);
                }
            }
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public boolean isCaretInTheBeginOfText() {
        return caret.getCaretListY() == 0 && caret.getCaretListX() == 0;
    }
    public boolean isCaretInTheEndOfText() {
        return caret.getCaretListY() == text.size() - 1 && caret.getCaretListX() == text.get(caret.getCaretListY()).size();
    }

    public boolean isCaretInTheFirstLine() {
        return caret.getCaretListY() == 0;
    }
    public boolean isCaretInTheLastLine() {
        return caret.getCaretListY() == text.size() - 1;
    }

    public boolean isCaretInTheBeginOfTheLine() {
        return caret.getCaretListX() == 0;
    }
    public boolean isCaretInTheEndOfLine() {
        return caret.getCaretListX() == text.get(caret.getCaretListY()).size();
    }

    public boolean isCaretAfterTheLineEnd() {
        return caret.getCaretListX() > text.get(caret.getCaretListY()).size();
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public void changeFontType(ActionEvent actionEvent){
        JComboBox comboBox = (JComboBox) actionEvent.getSource();
        String fontType = (String) comboBox.getSelectedItem();
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    charElement.setFontType(fontType);
                }
            }
        }
    }
    public void changeFontSize(ActionEvent actionEvent){
        JComboBox comboBox = (JComboBox) actionEvent.getSource();
        String fontSize = (String) comboBox.getSelectedItem();
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    charElement.setFontSize(Integer.parseInt(fontSize));
                }
            }
        }
    }

    public void setFontType(String fontType){
        font = new Font(fontType, getFontStyle(), getFontSize());
    }
    public void setFontSize(int fontSize){
        font = new Font(getFontType(), getFontStyle(), fontSize);
    }

    public String getFontType(){
        return font.getFontName();
    }
    public int getFontSize(){
        return font.getSize();
    }
    public int getFontStyle(){
        return fontStyle;
    }
    public Font getFont(){
        return font;
    }
    public Caret getCaret(){
        return caret;
    }
    public List<Line> getText() {
        return text;
    }

    public void insertKeyChar(char charKey, int X, int Y){
        text.get(Y).addChar(X, charKey);
    }
    public void add(Line line){
        text.add(line);
    }
    public void falseAlSelection(){
        for (Line line : text){
            for (Char charElement : line.getLine()){
                charElement.setIsSelect(false);
            }
        }
    }
    public void removeLine(int Y){
        text.remove(Y);
    }

    public Line get(int Y){
        return text.get(Y);
    }


}

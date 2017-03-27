package TextEditor;

import javax.swing.*;
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
    private List<Line> text = new ArrayList<Line>();

    public Text(TextPanel textPanel){
        this.textPanel = textPanel;
        font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
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
        }else if(!isCaretInTheBeginOfLine()){
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
    public Point followCaret(int wight){
        int x = getCaret().getCaretCoordinateX() > wight ? getCaret().getCaretCoordinateX() : 0;
        int y = getCaret().getCaretCoordinateY() - getText().get(getCaret().getCaretListY()).getMaxHigh();
        return new Point(x, y);
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
                        charElement.setIsSelect(isElementHere(new Point(X, Y), charElement));
                    } else if (isElementHere(new Point(X, Y), charElement)) {
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
        if (!isCaretInTheBeginOfLine() && beforeIncrement != afterIncrement) {
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            for (Line line : text) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(isElementHere(new Point(X, Y), charElement));
                    } else if (isElementHere(new Point(X, Y), charElement)) {
                        charElement.setIsSelect(false);
                    }
                }
            }
        }

    }
    public void upSelection() {
        int firstX, firstY, secondX, secondY;
        if (!isCaretInTheBeginOfText() && !text.get(caret.getCaretListY()).isEmpty()) {
            if (!isCaretInTheBeginOfLine()){
                firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
                firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            } else{
                firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getX() - 1;
                firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getY() - 1;
            }
            decrementY();
            if (text.get(caret.getCaretListY()).isEmpty()){
                secondX = text.get(caret.getCaretListY() + 1).getLine().get(0).getX() - 1;
                secondY = text.get(caret.getCaretListY() + 1).getLine().get(0).getY() - 1;
            } else if (isCaretInTheBeginOfLine()){
                secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getX() - 1;
                secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getY() - 1;
            } else {
                secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
                secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            }
            selectElement(firstX, firstY, secondX, secondY);
        } else if (!isCaretInTheBeginOfText()){
            decrementX();
        }
    }
    public void downSelection() {
        int firstX, firstY, secondX, secondY;
        if (!isCaretInTheEndOfText() && (!text.get(caret.getCaretListY()).isEmpty())) {
            if (!isCaretInTheEndOfLine()){
                firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getX() - 1;
                firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getY() - 1;
            } else{
                firstX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
                firstY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            }
            incrementY();
            if (text.get(caret.getCaretListY()).isEmpty()){
                secondX = text.get(caret.getCaretListY() - 1).getLine().get(text.get(caret.getCaretListY() - 1).size() - 1).getX() + 1;
                secondY = text.get(caret.getCaretListY() - 1).getLine().get(text.get(caret.getCaretListY() - 1).size() - 1).getY() - 1;
            } else if (isCaretInTheBeginOfLine()){
                secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getX() - 1;
                secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX()).getY() - 1;
            } else {
                secondX = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
                secondY = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            }
            selectElement(firstX, firstY, secondX, secondY);
        } else if(!isCaretInTheEndOfText()){
            incrementY();
        }
    }
    public void selectElement(int firstX, int firstY, int secondX, int secondY){
        for (Line line : getText()) {
            for (Char charElement : line.getLine()) {
                if (!charElement.isSelect()) {
                    charElement.setIsSelect((isElementHere(new Point(firstX, firstY), new Point(secondX, secondY), charElement)));
                } else if (isElementHere(new Point(firstX, firstY), new Point(secondX, secondY), charElement)) {
                    charElement.setIsSelect(false);
                }
            }
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void deletePreviousChar() {
        if (isCaretInTheBeginOfText()) {
        } else if (isCaretInTheBeginOfLine()) {
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
                    element +=charElement.getStringElement();
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
                    text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), string.charAt(index), font);
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
        System.out.println(point.getX() + " " + point.getY());
        falseAlSelection();
        for (Line line: text){
            borderOfLine(point, line);
            for (Char charElement : line.getLine()){
                if(isElementHere(point, charElement)){
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
                charElement.setIsSelect(isElementHere(firstPoint, secondPoint, charElement));
                if (isElementHere(secondPoint, charElement)){
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

    public boolean isCaretInTheBeginOfLine() {
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
        setFontType(fontType);
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    charElement.setFontType(fontType);
                }
            }
        }
    }
    public void changeFontSize(int fontSize){
        setFontSize(fontSize);
        caret.setFont(font);
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    charElement.setFontSize(fontSize);
                }
            }
        }
    }
    public void changeFontStyle(int fontStyle){
        setFontStyle(fontStyle);
        caret.setFont(font);
        for (Line line : text){
            for (Char charElement : line.getLine()){
                if (charElement.isSelect()){
                    charElement.setFontStyle(getFontStyle());
                }
            }
        }
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public void setFontType(String fontType){
        font = new Font(fontType, getFontStyle(), getFontSize());
    }
    public void setFontSize(int fontSize){
        font = new Font(getFontType(), getFontStyle(), fontSize);
    }
    public void setFontStyle(int fontStyle){
         if (font.getStyle() == Font.BOLD + Font.ITALIC && fontStyle == Font.BOLD) {
            font = font.deriveFont(Font.ITALIC);
        } else if (font.getStyle() == Font.BOLD + Font.ITALIC && fontStyle == Font.ITALIC) {
            font = font.deriveFont(Font.BOLD);
        } else if (font.isPlain() && fontStyle == Font.BOLD) {
            font = font.deriveFont(Font.BOLD);
        } else if (font.isBold() && fontStyle == Font.BOLD) {
            font = font.deriveFont(Font.PLAIN);
        } else if (font.isPlain() && fontStyle == Font.ITALIC) {
            font = font.deriveFont(Font.ITALIC);
        } else if (font.isItalic() && fontStyle == Font.ITALIC) {
            font = font.deriveFont(Font.PLAIN);
        } else if (font.isBold() && fontStyle == Font.ITALIC) {
            font = font.deriveFont(Font.BOLD + Font.ITALIC);
        } else if (font.isItalic() && fontStyle == Font.BOLD) {
            font = font.deriveFont(Font.BOLD + Font.ITALIC);
        }
    }

    public boolean isElementHere (Point click, Char charElement){
        int X = charElement.getX();
        int Y = charElement.getY();
        int wight = charElement.getWight();
        int height = charElement.getHeight();
        return (click.getX() >= X &&
                click.getY() <= Y &&
                click.getX() <= X + wight &&
                click.getY() >= Y - height);
    }
    public boolean isElementHere (Point firstPoint, Point secondPoint, Char charElement){
        int Y = charElement.getY();
        int X = charElement.getX();
        int maxHeight = text.get(charElement.getNumberOfLine()).getMaxHigh();
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





    public String getFontType(){
        return font.getFontName();
    }
    public int getFontSize(){
        return font.getSize();
    }
    public int getFontStyle(){
        return font.getStyle();
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
        text.get(Y).addChar(X, charKey, font);
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

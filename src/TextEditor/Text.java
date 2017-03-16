package TextEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shund on 16.03.2017.
 */
public class Text {
    private Caret caret;
    private List<Line> text = new ArrayList<Line>();

    public Text(Caret caret){
        this.caret = caret;
    }

    public List<Line> getText() {
        return text;
    }

    public void insertKeyChar(char charKey){
        deleteSelectedText();
        text.get(caret.getCaretListY()).addChar(caret.getCaretListX(), charKey);
        caret.incrementX();
    }

    public void add(Line line){
        text.add(line);
    }

    public void enterLine() {
        int Y = caret.getCaretListY();
        int X = caret.getCaretListX();
        Line newLine = text.get(Y).copyFromX1toX2(X, text.get(Y).size());
        text.get(Y).remove(X, text.get(Y).size());
        text.add(Y + 1, newLine);
        caret.setCaretListX(0);
        caret.incrementY();
    }

    public void deletePreviousChar() {
        if (caret.getCaretListX() == 0 && caret.getCaretListY() == 0) {
        } else if (caret.getCaretListX() == 0) {
            caret.setCaretListX(getText().get(caret.getCaretListY() - 1).size());
            if (text.get(caret.getCaretListY()).size() != 0) {
                for (Char charElement : text.get(caret.getCaretListY()).getLine()) {
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

    public void deleteNextChar() {
        if (caret.getCaretListX() == text.get(caret.getCaretListY()).size() && caret.getCaretListY() == text.size() - 1) {
        } else if (caret.getCaretListX() == text.get(caret.getCaretListY()).size()) {
            if (text.get(caret.getCaretListY() + 1).size() != 0) {
                for (Char charElement : text.get(caret.getCaretListY() + 1).getLine()) {
                    text.get(caret.getCaretListY()).add(charElement);
                }
            }
            text.remove(caret.getCaretListY() + 1);
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
                if (!newLine && caret.getCaretListX() == text.get(caret.getCaretListY()).getLine().size()) {
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
        List<Char> line1 = text.get(caret.getCaretListY()).getLine();
        if (caret.getCaretListX() != line1.size() && beforeDecrement != afterDecrement) {
            int X = line1.get(caret.getCaretListX()).getX() + 1;
            int Y = line1.get(caret.getCaretListX()).getY() - 1;
            for (Line line : text) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        if (text.indexOf(line) == 0 && line.indexOf(charElement) == 0) {
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
        boolean b = caret.getCaretListX() != 0 && beforeIncrement != afterIncrement;
        if (b) {
            int X = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getX() + 1;
            int Y = text.get(caret.getCaretListY()).getLine().get(caret.getCaretListX() - 1).getY() - 1;
            for (Line line : text) {
                for (Char charElement : line.getLine()) {
                    if (!charElement.isSelect()) {
                        charElement.setIsSelect(charElement.isElementHere(new Point(X, Y)));
                    } else if (charElement.isElementHere(new Point(X, Y))) {
                        if (text.indexOf(line) == text.size() - 1 && line.getLine().indexOf(charElement) == line.size() - 1) {
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
        for (Line line : text) {
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

    public void falseAlSelection(){
        for (Line line : text){
            for (Char charElement : line.getLine()){
                charElement.setIsSelect(false);
            }
        }
    }
}

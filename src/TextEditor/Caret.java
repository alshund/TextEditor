package TextEditor;

import java.awt.*;
import java.util.List;

/**
 * Created by shund on 01.03.2017.
 */
public class Caret {
    private Text text;
    private int caretListX;
    private int caretListY;
    private int caretCoordinateX;
    private int caretCoordinateY;
    
    public Caret(Text text){
        this.text = text;
        caretCoordinateX = 10;
        caretCoordinateY = 10;
    }

    public void incrementX(){
        if (isCaretInTheEndOfText()){
        } else if (isCaretBeforeTheLineEnd()){
            caretListX++;
        } else if(isCaretBeforeTheLastLine()){
            caretListY++;
            setCaretListX(0);
        }
    }
    public void incrementY(){
        if(isCaretBeforeTheLastLine()){
            caretListY++;
            if (isCaretAfterTheLineEnd()){
                setCaretListX(text.getText().get(getCaretListY()).size());
            }
        } else {
            setCaretListX(text.getText().get(caretListY).getLine().size());
        }
    }
    public void decrementX(){
        if (isCaretInTheBeginOfText()){
        }else if(!isCaretInTheBeginOfTheLine()){
            caretListX--;
        }else if (isCaretInTheFirstLine()){
            caretListY--;
            setCaretListX(text.getText().get(getCaretListY()).size());
        }

    }
    public void decrementY(){
        if(!isCaretInTheFirstLine()){
            caretListY--;
            if (isCaretAfterTheLineEnd()){
                setCaretListX(text.getText().get(getCaretListY()).size());
            }
        } else{
            setCaretListX(0);
        }
    }

    public void deletePreviousChar() {
        if (isCaretInTheBeginOfText()) {
        } else if (!isCaretInTheBeginOfTheLine()) {
            setCaretListX(text.get(getCaretListY() - 1).size());
            if (text.get(caretListY).size() != 0) {
                for (Char charElement : text.getText().get(caretListY).getLine()) {
                    text.get(caretListY - 1).getLine().add(charElement);
                }
            }
            text.remove(caretListY);
            decrementY();
        } else {
            text.get(caretListY).remove(caretListX - 1, caretListX);
            decrementX();
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

    private boolean isCaretInTheBeginOfText() {
        return caretListY == 0 && caretListX == 0;
    }
    private boolean isCaretInTheEndOfText() {
        return caretListY == text.getText().size() - 1 && caretListX == text.getText().get(getCaretListY()).size();
    }
    private boolean isCaretBeforeTheLastLine() {
        return caretListY < text.getText().size() - 1;
    }
    private boolean isCaretBeforeTheLineEnd() {
        return caretListX < text.getText().get(getCaretListY()).size();
    }
    private boolean isCaretAfterTheLineEnd() {
        return caretListX > text.getText().get(getCaretListY()).size();
    }
    private boolean isCaretInTheFirstLine() {
        return caretListY == 0;
    }
    private boolean isCaretInTheBeginOfTheLine() {
        return caretListX == 0;
    }

    public int getCaretListX(){
        return caretListX;
    }
    public int getCaretListY(){
        return caretListY;
    }
    public int getCaretCoordinateX(){
        return caretCoordinateX;
    }
    public int getCaretCoordinateY(){
        return caretCoordinateY;
    }

    public void setCaretListX(int coordinateX){
        caretListX = coordinateX;
    }
    public void setCaretListY(int coordinateY){
        caretListY = coordinateY;
    }
    public void setCaretCoordinateX(int coordinateX){
        caretCoordinateX = coordinateX;
    }
    public void setCaretCoordinateY(int coordinateY){
        caretCoordinateY = coordinateY;
    }
}
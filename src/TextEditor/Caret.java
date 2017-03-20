package TextEditor;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
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
        } else if (!isCaretInTheEndOfLine()){
            caretListX++;
        } else if(!isCaretInTheLastLine()){
            caretListY++;
            setCaretListX(0);
        }
    }
    public void incrementY(){
        if(!isCaretInTheLastLine()){
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
        }else if (!isCaretInTheFirstLine()){
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











    public void borderOfLine(Point click, Line line){
        if (line.getCoordinateY() - line.getMaxHigh() <= click.getY()/* && coordinateY >= click.getY() && maxLength <= click.getX()*/){
            setCaretListY(line.getNumberOfLine());
            setCaretListX(click.getX() <= 10 && line.getCoordinateY() >= click.getY() ? 0 : line.size());
        }
    }



    public boolean isCaretInTheBeginOfText() {
        return caretListY == 0 && caretListX == 0;
    }
    public boolean isCaretInTheEndOfText() {
        return caretListY == text.getText().size() - 1 && caretListX == text.getText().get(getCaretListY()).size();
    }

    public boolean isCaretInTheFirstLine() {
        return caretListY == 0;
    }
    public boolean isCaretInTheBeginOfTheLine() {
        return caretListX == 0;
    }

    public boolean isCaretInTheLastLine() {
        return caretListY == text.getText().size() - 1;
    }
    public boolean isCaretInTheEndOfLine() {
        return caretListX == text.getText().get(caretListY).size();
    }

    public boolean isCaretAfterTheLineEnd() {
        return caretListX > text.getText().get(getCaretListY()).size();
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
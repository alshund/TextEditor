package TextElement;

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
    private Font font;
    private int caretListX;
    private int caretListY;
    private int caretCoordinateX;
    private int caretCoordinateY;
    /*----------------------------------------------------------------------------------------------------------------*/
    public Caret(){
        caretListX = 0;
        caretListY = 0;
    }
    /*----------------------------------------------------------------------------------------------------------------*/
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
    /*----------------------------------------------------------------------------------------------------------------*/
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
    public void setFont(Font font){
        this.font = font;
    }
    public Font getFont(){
        return font;
    }
}
package TextEditor;

import javax.swing.*;
import java.awt.*;

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



    public void incrementX(){
        if (isCaretBeforeTheTextEnd()){
        } else if (isCaretBeforeTheLineEnd()){
            caretListX++;
        } else if(isCaretBeforeTheLastLine()){
            caretListY++;
            setCaretListX(0);
        }
    }

    private boolean isCaretBeforeTheTextEnd() {
        return caretListY < text.getText().size() - 1;
    }

    private boolean isCaretBeforeTheLineEnd() {
        return caretListX < text.getText().get(getCaretListY()).size();
    }

    private boolean isCaretBeforeTheLastLine() {
        return caretListY == text.getText().size() - 1 && caretListX == text.getText().get(getCaretListY()).size();
    }

    public void incrementY(){
        if(isCaretYBeforeTheTextEnd()){
            caretListY++;
            if (caretListX > text.getText().get(getCaretListY()).size()){
                setCaretListX(text.getText().get(getCaretListY()).size());
            }
        } else {
            setCaretListX(text.getText().get(caretListY).getLine().size());
        }
    }
    public void decrementX(){
        if (caretListY == 0 && caretListX == 0){
        }else if(caretListX != 0){
            caretListX--;
        }else if (caretListY != 0){
            caretListY--;
            setCaretListX(text.getText().get(getCaretListY()).size());
        }

    }
    public void decrementY(){
        if(caretListY != 0){
            caretListY--;
            if (caretListX > text.getText().get(getCaretListY()).size()){
                setCaretListX(text.getText().get(getCaretListY()).size());
            }
        } else{
            setCaretListX(0);
        }
    }

}

package TextEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shund on 01.03.2017.
 */
public class Caret {
    private FrameWindow frameWindow;
    private TextPanel textPanel;
    private Font caretFont;
    private int caretListX;
    private int caretListY;
    private int caretCoordinateX;
    private int caretCoordinateY;


    public Caret(FrameWindow frameWindow){
        this.frameWindow = frameWindow;
        textPanel = frameWindow.getTextPanel();
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


    public void drawCaret(){
        Graphics2D graphics2D = (Graphics2D) textPanel.getGraphics();
        graphics2D.drawString("|", getCaretCoordinateX(), getCaretCoordinateY());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2D.setColor(textPanel.getBackground());
        graphics2D.drawString("|", getCaretCoordinateX(), getCaretCoordinateY());
//        textPanel.repaint();
    }
    public void incrementX(){
        if (caretListY == textPanel.getText().size() - 1 && caretListX == textPanel.getText().get(getCaretListY()).size()){
        } else if (caretListX < textPanel.getText().get(getCaretListY()).size()){
            caretListX++;
        } else if(caretListY < textPanel.getText().size() - 1){
            caretListY++;
            setCaretListX(0);
        }
    }
    public void incrementY(){
        if(caretListY < textPanel.getText().size() - 1){
            caretListY++;
            if (caretListX > textPanel.getText().get(getCaretListY()).size()){
                setCaretListX(textPanel.getText().get(getCaretListY()).size());
            }
        } else {
            setCaretListX(textPanel.getText().get(caretListY).getLine().size());
        }
    }
    public void decrementX(){
        if (caretListY == 0 && caretListX == 0){
        }else if(caretListX != 0){
            caretListX--;
        }else if (caretListY != 0){
            caretListY--;
            setCaretListX(textPanel.getText().get(getCaretListY()).size());
        }

    }
    public void decrementY(){
        if(caretListY != 0){
            caretListY--;
            if (caretListX > textPanel.getText().get(getCaretListY()).size()){
                setCaretListX(textPanel.getText().get(getCaretListY()).size());
            }
        } else{
            setCaretListX(0);
        }
    }

    public void followCaret(){
        int x = 0;
        if(getCaretListX() > frameWindow.getFrameWindow().getWidth()){
            x = textPanel.getCaret().getCaretCoordinateX();
        }
        int y = textPanel.getCaret().getCaretCoordinateY() - textPanel.getText().get(textPanel.getCaret().getCaretListY()).getMaxHigh();
        JViewport scrollP = frameWindow.getScrollPane().getViewport();
        scrollP.setViewPosition(new Point(x, y));
        frameWindow.getScrollPane().setViewport(scrollP);
    }
}

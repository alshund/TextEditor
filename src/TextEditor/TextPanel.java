package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * Created by shund on 28.02.2017.
 */
public class TextPanel extends JComponent {
    private FrameWindow frameWindow;
    private Text text;

    public TextPanel (FrameWindow frameWindow){
        text = new Text(this);
        this.frameWindow = frameWindow;
    }

    private void setFont(Graphics2D graphics2D){
        for (Line line : text.getText()){
            line.setMaxHighNumber(0);
            for (Char charElement : line.getLine()){
                Font font = new Font(charElement.getFontType(), 0, charElement.getFontSize());
                graphics2D.setFont(font);

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
        if (text.getCaret().getCaretListX() == numberOfChar && text.getCaret().getCaretListY() == numberOfLine){
            text.getCaret().setCaretCoordinateX(X);
            text.getCaret().setCaretCoordinateY(Y);
        }
        if (text.getCaret().getCaretListX() == 0 && text.getCaret().getCaretListY() == numberOfLine){
            text.getCaret().setCaretCoordinateX(10);
            text.getCaret().setCaretCoordinateY(Y);
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
                Font font = new Font(charElement.getFontType(), 0, charElement.getFontSize());
                graphics2D.setFont(font);
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
        graphics2D.drawString("|", text.getCaret().getCaretCoordinateX(), text.getCaret().getCaretCoordinateY());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2D.setColor(this.getBackground());
        graphics2D.drawString("|", text.getCaret().getCaretCoordinateX(), text.getCaret().getCaretCoordinateY());
//        textPanel.repaint();
    }















    public Text getText(){
        return text;
    }

}

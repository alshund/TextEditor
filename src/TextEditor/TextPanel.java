package TextEditor;

import TextElement.Char;
import TextElement.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by shund on 28.02.2017.
 */
public class TextPanel extends JComponent {
    private FrameWindow frameWindow;
    private Text text;
    private final int START_COORDINATE_X = 10;
    private final int START_COORDINATE_Y = 0;

    public TextPanel (FrameWindow frameWindow){
        text = new Text(this);
        this.frameWindow = frameWindow;
        setLayout(new BorderLayout());
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        setFont(graphics2D);
        paintChar(graphics2D);
    }

    private void setFont(Graphics2D graphics2D){
        int descent = 0;
        for (Line line : text.getText()){
            line.setMaxHigh(0, true);
            for (Char charElement : line.getLine()){
                Font font = new Font(charElement.getFontType(), charElement.getFontStyle(), charElement.getFontSize());
                graphics2D.setFont(font);
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                line.setMaxHigh(charElement.getFontSize() + descent, false);
                line.setMaxDescent(fontMetrics.getDescent());
            }
            if (line.getMaxHigh() == 0){
                Font font = text.getFont();
                line.setMaxHigh(font.getSize() + descent, true);
            }
            descent = line.getMaxDescent();
        }
    }

    private void paintChar(Graphics2D graphics2D){
        int Y = START_COORDINATE_Y, numberOfLine = 0, xMax = 0;
        for (Line line : text.getText()){
            Y += line.getMaxHigh();
            int X = START_COORDINATE_X, numberOfChar = 0;
            for (Char charElement : line.getLine()){
                graphics2D.setColor(Color.BLACK);
                Font font = charElement.getFont();
                graphics2D.setFont(font);
                graphics2D.drawString(charElement.getStringElement(), X, Y);

                createSelectionArea(line, charElement, graphics2D, X, Y);

                numberOfChar++;
                X += setChar(charElement, X, Y, numberOfLine, graphics2D);

                setCaret(X, Y, numberOfLine, numberOfChar);
            }
            setLine(line, X, numberOfLine, Y);
            setCaret(X, Y, numberOfLine, numberOfChar);
            numberOfLine++;
            xMax = xMax < X ? X : xMax;
        }
        setPreferredSize(new Dimension(xMax + 70, Y + 70));
    }

    private void createSelectionArea(Line line, Char charElement, Graphics2D graphics2D, int X, int Y){
        if (charElement.isSelect()){
            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            graphics2D.setColor(new Color(200, 200, 200, 120));
            Rectangle2D rectangle2D = new Rectangle(X - 2, Y - line.getMaxHigh() + 2, fontMetrics.stringWidth(charElement.getStringElement()) + 3, line.getMaxHigh());
            graphics2D.draw(rectangle2D);
            graphics2D.fill(rectangle2D);
        }
    }

    private int setChar(Char charElement, int X, int Y, int numberOfLine, Graphics2D graphics2D){
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        Font font = text.getFont();
        charElement.setX(X);
        charElement.setY(Y);
        charElement.setNumberOfLine(numberOfLine);
        charElement.setHeight(font.getSize());
        charElement.setWight(fontMetrics.stringWidth(charElement.getStringElement()));
        return (fontMetrics.stringWidth(charElement.getStringElement()) + 1);
    }

    private void setCaret(int X, int Y, int numberOfLine, int numberOfChar){
        if (text.getCaret().getCaretListX() == numberOfChar && text.getCaret().getCaretListY() == numberOfLine){
            text.getCaret().setCaretCoordinateX(X);
            text.getCaret().setCaretCoordinateY(Y);
        }
        if (text.getCaret().getCaretListX() == 0 && text.getCaret().getCaretListY() == numberOfLine){
            text.getCaret().setCaretCoordinateX(START_COORDINATE_X);
            text.getCaret().setCaretCoordinateY(Y);
        }
    }

    private void setLine(Line line,  int maxLength, int numberOfLine, int Y){
        line.setMaxLength(maxLength);
        line.setNumberOfLine(numberOfLine);
        line.setCoordinateY(Y);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void drawCaret(){
        int caretCoordinateX = text.getCaret().getCaretCoordinateX();
        int caretCoordinateY = text.getCaret().getCaretCoordinateY();
        Graphics2D graphics2D = (Graphics2D) this.getGraphics();
        Font caretFont = text.getCaret().getFont();
        graphics2D.setFont(caretFont);
        FontMetrics fontMetrics =  graphics2D.getFontMetrics();
        graphics2D.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fontMetrics.getHeight()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2D.setColor(this.getBackground());
        graphics2D.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fontMetrics.getHeight()));
//        textPanel.repaint();
    }

    public Text getText(){
        return text;
    }

}

package TextElement;

import java.awt.*;

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
    public Caret(Font font) {
        this.font = font;
        caretListX = 0;
        caretListY = 0;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public int getCaretListX() {
        return caretListX;
    }

    public int getCaretListY() {
        return caretListY;
    }

    public int getCaretCoordinateX() {
        return caretCoordinateX;
    }

    public int getCaretCoordinateY() {
        return caretCoordinateY;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void setCaretListX(int coordinateX) {
        caretListX = coordinateX;
    }

    public void setCaretListY(int coordinateY) {
        caretListY = coordinateY;
    }

    public void setCaretCoordinateX(int coordinateX) {
        caretCoordinateX = coordinateX;
    }

    public void setCaretCoordinateY(int coordinateY) {
        caretCoordinateY = coordinateY;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void setFont(Font font) {
        this.font = font;
    }

    public void setFontType(String fontType) {
        font = new Font(fontType, getFontStyle(), getFontSize());
    }

    public void setFontSize(int fontSize) {
        font = font.deriveFont((float) fontSize);
    }

    public void setFontStyle(int fontStyle){
        font = font.deriveFont(fontStyle);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public Font getFont() {
        return font;
    }

    public String getFontType() {
        return font.getFontName();
    }

    public int getFontSize() {
        return font.getSize();
    }

    public int getFontStyle() {
        return font.getStyle();
    }
}
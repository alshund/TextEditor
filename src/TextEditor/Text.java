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
        int X = caret.getCaretListX();
        int Y = caret.getCaretListY();
        Line newLine = text.get(Y).copyFromX1toX2(X, text.get(Y).size());
        text.get(Y).remove(X, text.get(Y).size());
        text.add(Y + 1, newLine);
        caret.setCaretListX(0);
        caret.incrementY();
    }

    public Line get(int Y){
        return text.get(Y);
    }
    public void remove(int Y){
        text.remove(Y);
    }

    public void falseAlSelection(){
        for (Line line : text){
            for (Char charElement : line.getLine()){
                charElement.setIsSelect(false);
            }
        }
    }
}

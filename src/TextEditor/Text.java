package TextEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shund on 16.03.2017.
 */
public class Text {
    private List<Line> text = new ArrayList<Line>();

    public Text(){

    }

    public List<Line> getText() {
        return text;
    }

    public void insertKeyChar(char charKey, int X, int Y){
        text.get(Y).addChar(X, charKey);
    }

    public void add(Line line){
        text.add(line);
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

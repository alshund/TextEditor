package TextElement;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shund on 28.02.2017.
 */
public class Line {
    private List<Char> line = new LinkedList<Char>();
    private int maxHigh;
    private int leading;
    private int maxLength;
    private int numberOfLine;
    private int coordinateY;

    public Line() {
    }

    public Line subline(int firstIndex, int secondIndex) {
        Line newLine = new Line();
        for (int index = firstIndex; index < secondIndex; index++) {
            newLine.add(this.line.get(index));
        }
        return newLine;
    }
    public void remove(int firstIndex, int secondIndex) {
        int index = firstIndex;
        while (index < secondIndex) {
            this.line.remove(firstIndex);
            index++;
        }
    }

    public List<Char> getLine() {
        return line;
    }

    public int getMaxHigh() {
        return maxHigh;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int size() {
        return line.size();
    }

    public int indexOf(Char charElement) {
        return line.indexOf(charElement);
    }

    public void setMaxLeading(int leading){
        if (this.leading > this.leading){
            this.leading = this.leading;
        }
    }
    public int getMaxLeading(){
        return leading;
    }
    public void setMaxHigh(int maxHigh) {
        if (maxHigh > this.maxHigh) {
            this.maxHigh = maxHigh;
        }
    }

    public void setMaxHighNumber(int n) {
        maxHigh = n;
    }

    public void setMaxLength(int length) {
        maxLength = length;
    }

    public void setNumberOfLine(int numberOfLine) {
        this.numberOfLine = numberOfLine;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void addChar(int coordinateX, char charElement, Font font) {
        line.add(coordinateX, new Char(charElement, font));
//        System.out.println(coordinateX + "-" + charElement);
    }

    public void add(Char charElement) {
        line.add(charElement);
    }

    public void add(String stringElement, String font, String style, String size) {
        line.add(new Char(stringElement.charAt(0), new Font(font, Integer.parseInt(style), Integer.parseInt(size))));
    }




    public boolean isEmpty() {
        return line.isEmpty();
    }

}
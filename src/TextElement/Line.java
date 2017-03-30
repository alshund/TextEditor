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
    private int maxDescent;
    private int maxLength;
    private int numberOfLine;
    private int coordinateY;

    public Line() {
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void add(Char charElement) {
        line.add(charElement);
    }

    public void add(String stringElement, String font, String style, String size) {
        line.add(new Char(stringElement.charAt(0), new Font(font, Integer.parseInt(style), Integer.parseInt(size))));
    }

    public void add(int coordinateX, char charElement, Font font) {
        line.add(coordinateX, new Char(charElement, font));
    }

    /*----------------------------------------------------------------------------------------------------------------*/
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

    public int size() {
        return line.size();
    }

    public int indexOf(Char charElement) {
        return line.indexOf(charElement);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public boolean isEmpty() {
        return line.isEmpty();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void setMaxHigh(int high, boolean isLineEmpty) {
        if (high > this.maxHigh && !isLineEmpty) {
            this.maxHigh = high;
        } else if (isLineEmpty) {
            this.maxHigh = high;
        }
    }

    public void setMaxDescent(int descent) {
        if (descent > this.maxDescent) {
            this.maxDescent = descent;
        }
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

    /*----------------------------------------------------------------------------------------------------------------*/
    public List<Char> getLine() {
        return line;
    }

    public int getMaxHigh() {
        return maxHigh;
    }

    public int getMaxDescent() {
        return maxDescent;
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
}
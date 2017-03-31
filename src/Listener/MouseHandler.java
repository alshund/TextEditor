package Listener;

import TextEditor.FrameWindow;
import TextEditor.Text;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by shund on 05.03.2017.
 */
public class MouseHandler implements MouseInputListener {
    private FrameWindow frameWindow;
    private Text text;
    private Point click;

    public MouseHandler(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        text = frameWindow.getTextPanel().getText();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        text.mouseClick(mouseEvent.getPoint());
        changeTypeComboBox();
        changeSizeComboBox();
        frameWindow.unloadFrameWindow();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        click = mouseEvent.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (frameWindow.getTextPanel().getComponentPopupMenu().isPopupTrigger(mouseEvent)) {
            frameWindow.getTextPanel().getComponentPopupMenu().show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
        }
        text.mouseClick(click, mouseEvent.getPoint());
        changeTypeComboBox();
        changeSizeComboBox();
        frameWindow.unloadFrameWindow();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        text.mouseClick(click, mouseEvent.getPoint());
        frameWindow.unloadFrameWindow();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void changeSizeComboBox() {
        JComboBox comboSize = frameWindow.getComboFontSize();
        String size = Integer.toString(text.getCaret().getFont().getSize());
        comboSize.setSelectedItem((Object) size);
    }

    private void changeTypeComboBox() {
        JComboBox comboType = frameWindow.getComboFontType();
        String type = text.getCaret().getFont().getFontName();
        comboType.setSelectedItem((Object) type);
    }
}

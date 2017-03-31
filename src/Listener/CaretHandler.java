package Listener;

import TextEditor.FrameWindow;
import TextEditor.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 11.03.2017.
 */
public class CaretHandler implements KeyListener {
    private FrameWindow frameWindow;
    private Text text;

    public CaretHandler(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        text = frameWindow.getTextPanel().getText();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            text.moveCaretLeft();
        } else if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            text.moveCaretUp();
        } else if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            text.moveCaretRight();
        } else if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            text.moveCaretDown();
        }
        changeTypeComboBox();
        changeSizeComboBox();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
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

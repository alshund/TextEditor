package Listener;

import TextEditor.FrameWindow;
import TextEditor.Text;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 13.03.2017.
 */
public class ShiftHandler implements KeyListener {
    private Text text;

    public ShiftHandler(FrameWindow frameWindow) {
        text = frameWindow.getTextPanel().getText();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isLeftSelection(keyEvent)) {
            text.leftSelection();
        } else if (isUpSelection(keyEvent)) {
            text.upSelection();
        } else if (isRightSelection(keyEvent)) {
            text.rightSelection();
        } else if (isDownSelection(keyEvent)) {
            text.downSelection();
        }
    }

    private boolean isDownSelection(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_DOWN;
    }

    private boolean isRightSelection(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT;
    }

    private boolean isUpSelection(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getKeyCode() == keyEvent.VK_UP;
    }

    private boolean isLeftSelection(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getKeyCode() == keyEvent.VK_LEFT;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}

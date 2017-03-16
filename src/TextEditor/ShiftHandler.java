package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 13.03.2017.
 */
public class ShiftHandler implements KeyListener {
    private Caret caret;
    public ShiftHandler(FrameWindow frameWindow){
        this.caret = frameWindow.getTextPanel().getCaret();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isLeftSelection(keyEvent)){
            caret.leftSelection();
        } else if (isUpSelection(keyEvent)){
            caret.upSelection();
        } else if(isRightSelection(keyEvent)){
            caret.rightSelection();
        } else if (isDownSelection(keyEvent)){
            caret.downSelection();
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
    public void keyReleased(KeyEvent keyEvent) {}
}

package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 13.03.2017.
 */
public class ShiftHandler implements KeyListener {
    private TextPanel textPanel;
    public ShiftHandler(FrameWindow frameWindow){
        this.textPanel = frameWindow.getTextPanel();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isLeftSelection(keyEvent)){
            textPanel.leftSelection();
        } else if (isUpSelection(keyEvent)){
            textPanel.upSelection();
        } else if(isRightSelection(keyEvent)){
            textPanel.rightSelection();
        } else if (isDownSelection(keyEvent)){
            textPanel.downSelection();
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

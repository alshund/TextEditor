package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 13.03.2017.
 */
public class ShiftHandler implements KeyListener {
    private TextPanel textPanel;
    public ShiftHandler(FrameWindow frameWindow){
        textPanel = frameWindow.getTextPanel();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isLeftSelection(keyEvent)){
            textPanel.leftSelection();
        } else if (keyEvent.isShiftDown() && keyEvent.getKeyCode() == keyEvent.VK_UP){
            textPanel.upSelection();
        } else if(keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            textPanel.rightSelection();
        } else if (keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            textPanel.downSelection();
        }
    }

    private boolean isLeftSelection(KeyEvent keyEvent) {
        return keyEvent.isShiftDown() && keyEvent.getKeyCode() == keyEvent.VK_LEFT;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}

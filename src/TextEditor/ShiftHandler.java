package TextEditor;

import java.awt.event.KeyAdapter;
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
        if(keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            textPanel.selectNext();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

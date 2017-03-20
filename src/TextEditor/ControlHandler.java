package TextEditor;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by shund on 20.03.2017.
 */
public class ControlHandler implements KeyListener {
    private TextPanel textPanel;
    public ControlHandler(FrameWindow frameWindow){
        textPanel = frameWindow.getTextPanel();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C){
            textPanel.copy();
        }else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V){
            textPanel.paste();
        }else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X){
            textPanel.cut();

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

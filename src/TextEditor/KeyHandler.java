package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created  shund on 02.03.2017.
 */
public class KeyHandler implements KeyListener {
    private FrameWindow frameWindow;
    private TextPanel textPanel;

    public KeyHandler(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        textPanel = frameWindow.getTextPanel();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if(!keyEvent.isControlDown() && !isSystemKey(keyEvent)){
            textPanel.insertKeyChar(keyEvent.getKeyChar());
            frameWindow.unloadFrameWindow();
        }
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            textPanel.enterKey();
        }
//        textPanel.getCaret().followCaret();
        frameWindow.unloadFrameWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    private boolean isSystemKey(KeyEvent keyEvent) {
        return (keyEvent.getKeyChar() == (int)KeyEvent.VK_ENTER ||
                keyEvent.getKeyChar() == (int)KeyEvent.VK_BACK_SPACE ||
                keyEvent.getKeyChar() == (int)KeyEvent.VK_DELETE ||
                keyEvent.getKeyChar() == (int)KeyEvent.VK_ESCAPE);
    }
}


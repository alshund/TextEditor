package Listener;

import TextEditor.FrameWindow;
import TextEditor.Text;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by shund on 20.03.2017.
 */
public class ControlHandler implements KeyListener {
    private Text text;

    public ControlHandler(FrameWindow frameWindow) {
        text = frameWindow.getTextPanel().getText();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            text.copy();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
            text.paste();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
            text.cut();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_A){
            text.selectAllText();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}

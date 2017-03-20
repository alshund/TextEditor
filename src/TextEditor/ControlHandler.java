package TextEditor;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by shund on 20.03.2017.
 */
public class ControlHandler implements KeyListener {
    Caret caret;
    public ControlHandler(FrameWindow frameWindow){
        caret = frameWindow.getTextPanel().getCaret();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C){
            caret.copy();
        }else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V){
            caret.paste();
        }else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X){

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

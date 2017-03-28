package Listener;

import TextEditor.FrameWindow;
import TextEditor.Text;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 12.03.2017.
 */
public class DeleteHandler implements KeyListener {
    private Text text;
    public DeleteHandler(FrameWindow frameWindow){
        text = frameWindow.getTextPanel().getText();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if (!text.deleteSelectedText()) text.deletePreviousChar();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
            if (!text.deleteSelectedText()) text.deleteNextChar();
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}

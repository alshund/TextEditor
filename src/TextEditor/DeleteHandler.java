package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 12.03.2017.
 */
public class DeleteHandler implements KeyListener {
    private Caret caret;
    public DeleteHandler(FrameWindow frameWindow){
        this.caret = frameWindow.getTextPanel().getCaret();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if (!caret.deleteSelectedText()) caret.deletePreviousChar();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
            if (!caret.deleteSelectedText()) caret.deleteNextChar();
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}

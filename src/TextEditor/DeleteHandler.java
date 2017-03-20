package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 12.03.2017.
 */
public class DeleteHandler implements KeyListener {
    private TextPanel textPanel;
    public DeleteHandler(FrameWindow frameWindow){
        this.textPanel = frameWindow.getTextPanel();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if (!textPanel.deleteSelectedText()) textPanel.deletePreviousChar();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
            if (!textPanel.deleteSelectedText()) textPanel.deleteNextChar();
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}

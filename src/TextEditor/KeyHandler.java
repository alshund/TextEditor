package TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created  shund on 02.03.2017.
 */
public class KeyHandler implements KeyListener {
    private FrameWindow frameWindow;
    private TextPanel textPanel;
    private Text text;
    private Caret caret;

    public KeyHandler(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        this.text = frameWindow.getTextPanel().getText();
        this.textPanel = frameWindow.getTextPanel();
        this.caret = textPanel.getCaret();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if(!keyEvent.isControlDown() && !isSystemKey(keyEvent)){
            textPanel.deleteSelectedText();
            text.insertKeyChar(keyEvent.getKeyChar(), caret.getCaretListX(), caret.getCaretListY());
            caret.incrementX();
        }
        frameWindow.unloadFrameWindow();

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            textPanel.deleteSelectedText();
            textPanel.newLine();
        }
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


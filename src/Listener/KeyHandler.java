package Listener;

import TextElement.Caret;
import TextEditor.FrameWindow;
import TextEditor.Text;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created  shund on 02.03.2017.
 */
public class KeyHandler implements KeyListener {
    private FrameWindow frameWindow;
    private Text text;
    private Caret caret;

    public KeyHandler(FrameWindow frameWindow) {
        this.frameWindow = frameWindow;
        this.text = frameWindow.getTextPanel().getText();
        this.caret = frameWindow.getTextPanel().getText().getCaret();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (!keyEvent.isControlDown() && !isSystemKey(keyEvent)) {
            text.deleteSelectedText();
            text.insertKeyChar(keyEvent.getKeyChar(), caret.getCaretListX(), caret.getCaretListY());
            text.moveCaretRight();
        }
        frameWindow.setViewport(text.followCaret(frameWindow.getFrameWindow().getWidth()));
        frameWindow.unloadFrameWindow();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            text.enterLine();
        }
        frameWindow.setViewport(text.followCaret(frameWindow.getFrameWindow().getWidth()));
        frameWindow.unloadFrameWindow();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private boolean isSystemKey(KeyEvent keyEvent) {
        return (keyEvent.getKeyChar() == (int) KeyEvent.VK_ENTER ||
                keyEvent.getKeyChar() == (int) KeyEvent.VK_BACK_SPACE ||
                keyEvent.getKeyChar() == (int) KeyEvent.VK_DELETE ||
                keyEvent.getKeyChar() == (int) KeyEvent.VK_ESCAPE);
    }
}


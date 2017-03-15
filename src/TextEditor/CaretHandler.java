package TextEditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shund on 11.03.2017.
 */
public class CaretHandler implements KeyListener{
    private FrameWindow frameWindow;
    private TextPanel textPanel;
    private Caret caret;
    public CaretHandler(FrameWindow frameWindow){
        this.frameWindow = frameWindow;
        textPanel = frameWindow.getTextPanel();
        caret = frameWindow.getTextPanel().getCaret();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            textPanel.falseAlSelection();
            caret.decrementX();
        } else if (!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_UP){
            textPanel.falseAlSelection();
            caret.decrementY();
        } else if(!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            textPanel.falseAlSelection();
            caret.incrementX();
        } else if(!keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            textPanel.falseAlSelection();
            caret.incrementY();
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}

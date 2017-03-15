package TextEditor;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by shund on 05.03.2017.
 */
public class MouseHandler implements MouseInputListener {
    private TextPanel textPanel;
    private Point click;

    public MouseHandler(FrameWindow frameWindow){
        textPanel = frameWindow.getTextPanel();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent){
        textPanel.mouseClick(mouseEvent.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        click = mouseEvent.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        textPanel.mouseClick(click, mouseEvent.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        System.out.println("123");
        textPanel.mouseClick(click, mouseEvent.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

package TextEditor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shund on 06.03.2017.
 */
public class CaretTimer {
    private Caret caret;
    public CaretTimer(TextPanel textPanel){
        caret = textPanel.getCaret();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                caret.drawCaret();
            }
        }, 500, 1000);
    }
}

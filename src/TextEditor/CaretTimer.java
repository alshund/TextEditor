package TextEditor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shund on 06.03.2017.
 */
public class CaretTimer {
    public CaretTimer(TextPanel textPanel){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                textPanel.drawCaret();
            }
        }, 500, 1000);
    }
}

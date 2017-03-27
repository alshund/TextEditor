package TextEditor;

import javax.swing.*;

/**
 * Created by shund on 27.03.2017.
 */
public class FileHandler {
    private FrameWindow frameWindow;
    private Text text;
    public FileHandler(FrameWindow frameWindow){
        this.frameWindow = frameWindow;
    }
    public void openFile(){
        JFileChooser fileChooser = new JFileChooser();
//        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getPath();
            System.out.println(filePath);
        }
    }
}
